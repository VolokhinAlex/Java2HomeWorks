package ru.java_two.chat_server.core;

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

    private int port;
    private ServerSocketThread server;
    private final DateFormat DATE_FORMAT = new SimpleDateFormat("[HH:mm:ss] ");
    private Vector<SocketThread> users;

    public void start(int port) {
        if (server != null && server.isAlive()) {
            System.out.println("Server already started");
        } else {
            server = new ServerSocketThread(this, "server", port, 2000);
            users = new Vector<>();
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
        System.out.println(msg);
    }

    @Override
    public void onServerStarted(ServerSocketThread thread) {
        putLog("Server socket thread started");
    }

    @Override
    public void onServerStopped(ServerSocketThread thread) {
        putLog("Server socket thread stopped");
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
    public void onServerAccepted(ServerSocketThread thread, ServerSocket server, Socket socket) {
        putLog("Client Connection");
        String name = "SocketThread" + socket.getInetAddress() + ":" + socket.getPort();
        new SocketThread(name, this, socket);
    }

    @Override
    public void onServerException(ServerSocketThread thread, Throwable exception) {
        exception.printStackTrace();
    }

    @Override
    public void onSocketStart(SocketThread thread, Socket socket) {
        users.add(thread);
        putLog("Socket created");
//        for (int i = 0; i < users.size(); i++) {
//            System.out.println(users.get(i));
//        }
    }

    @Override
    public void onSocketStop(SocketThread thread) {
        users.remove(thread);
        putLog("Socket stopped");
    }

    @Override
    public void onSocketReady(SocketThread thread, Socket socket) {
        putLog("Socket is ready");
    }

    @Override
    public void onReceiveString(SocketThread thread, Socket socket, String msg) {
//            thread.sendMessage(msg);
//        for (int i = 0; i < users.size(); i++) {
//            users.get(i).sendMessage(msg);
//        }
        for (SocketThread user : users) {
            user.sendMessage(msg);
        }
    }

    @Override
    public void onSocketException(SocketThread thread, Exception exception) {
        exception.printStackTrace();
    }

}
