package ru.java_two.chat_server.core;

import chat_library.Protocol;
import ru.java_two.network.ServerSocketThread;
import ru.java_two.network.ServerSocketThreadListener;
import ru.java_two.network.SocketThread;
import ru.java_two.network.SocketThreadListener;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class ChatServer implements ServerSocketThreadListener, SocketThreadListener {

    private ServerSocketThread server;
    private final DateFormat DATE_FORMAT = new SimpleDateFormat("[HH:mm:ss] ");
    private Vector<SocketThread> users;

    private ChatServerListener listener;

    public ChatServer(ChatServerListener listener) {
        this.listener = listener;
        users = new Vector<>();
    }

    public void start(int port) {
        if (server != null && server.isAlive()) {
            System.out.println("Server already started");
        } else {
            server = new ServerSocketThread(this, "server", port, 2000);
        }
    }

    public void stop() {
        if (server == null || !server.isAlive()) {
            System.out.println("Server is not running");
        } else {
            server.interrupt();
        }
    }

    public void putLog(String msg) {
        msg = DATE_FORMAT.format(System.currentTimeMillis()) +
                Thread.currentThread().getName() + ": " + msg;
        listener.onChatServerMessage(msg);
    }

    @Override
    public void onServerStarted(ServerSocketThread thread) {
        putLog("Server socket thread started");
        SqlClient.connect();
    }

    @Override
    public void onServerStopped(ServerSocketThread thread) {
        putLog("Server socket thread stopped");
        SqlClient.disconnect();
        for (SocketThread user : users) {
            user.close();
        }
    }

    @Override
    public void onServerSocketCreated(ServerSocketThread thread, ServerSocket server) {
        putLog("Server socket created");
    }

    @Override
    public void onServerTimeout(ServerSocketThread thread, ServerSocket server) {
//        putLog("Server socket thread timeout accepted");
    }

    @Override
    public void onSocketAccepted(ServerSocketThread thread, ServerSocket server, Socket socket) {
        putLog("Client Connection");
        String name = "SocketThread" + socket.getInetAddress() + ":" + socket.getPort();
        new ClientThread(this, name, socket);
    }

    @Override
    public void onServerException(ServerSocketThread thread, Throwable exception) {
        exception.printStackTrace();
    }

    @Override
    public synchronized void onSocketStart(SocketThread thread, Socket socket) {
        putLog("Socket created");
    }

    @Override
    public synchronized void onSocketStop(SocketThread thread) {
        users.remove(thread);
    }

    @Override
    public synchronized void onSocketReady(SocketThread thread, Socket socket) {
        users.add(thread);
    }

    @Override
    public synchronized void onReceiveString(SocketThread thread, Socket socket, String msg) {
        ClientThread client = (ClientThread) thread;
        if (client.isAuthorized()) {
            handleAuthClientMessage(client, msg);
        } else {
            handleNonAuthClientMessage(client, msg);
        }
    }

    @Override
    public synchronized void onSocketException(SocketThread thread, Exception exception) {
        exception.printStackTrace();
    }

    public void handleAuthClientMessage(ClientThread user, String message) {
        sendToAllAuthorizedClients(Protocol.getTypeBroadcast(user.getNickname(), message));
    }

    public void handleNonAuthClientMessage(ClientThread user, String message) {
        String[] arrayUser = message.split(Protocol.DELIMITER);
        if (arrayUser.length != 3 || !arrayUser[0].equals(Protocol.AUTH_REQUEST)) {
            user.msgFormatError(message);
            System.out.println("length");
            return;
        }
        String login = arrayUser[1];
        String password = arrayUser[2];
        String nickname = SqlClient.getNickname(login, password);
        if (nickname == null) {
            putLog("Invalid credentials attempt for login = " + login);
            user.authFail();
            return;
        }
        user.authAccept(nickname);
        sendToAllAuthorizedClients(Protocol.getTypeBroadcast("Server", nickname + " connected"));
    }

    private void sendToAllAuthorizedClients(String msg) {
        for (int i = 0; i < users.size(); i++) {
            ClientThread recipient = (ClientThread) users.get(i);
            if (!recipient.isAuthorized()) continue;
            recipient.sendMessage(msg);
        }
    }

}
