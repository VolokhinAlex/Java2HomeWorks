package ru.java_two.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketThread extends Thread {
    private final SocketThreadListener listener;
    private final Socket socket;
    private DataOutputStream out;

    public SocketThread(String name, SocketThreadListener listener, Socket socket) {
        super(name);
        this.listener = listener;
        this.socket = socket;
        start();
    }

    @Override
    public void run() {
        try {
            listener.onSocketStart(this, socket);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            listener.onSocketReady(this, socket);
            while (!isInterrupted()) { // ���� ����� �������
                String msg = in.readUTF(); // � msg ������ ����.
                listener.onReceiveString(this, socket, msg); // ��������� ������
            }
        } catch (IOException ex) {
            listener.onSocketException(this, ex);
            close();
        } finally {
            listener.onSocketStop(this);
        }
    }

    public synchronized boolean sendMessage(String msg) {
        try {
            out.writeUTF(msg); // ������� ������
            out.flush(); // ��������� �����
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            close(); // ��������� ����� � �����
            return false;
        }
    }

    public synchronized void close() {
        try {
            out.close();
        } catch (IOException ex) {
            listener.onSocketException(this, ex);
        }
        interrupt();
        try {
            socket.close();
        } catch (IOException ex) {
            listener.onSocketException(this, ex);
        }
    }
}
