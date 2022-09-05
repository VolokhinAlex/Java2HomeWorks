package ru.java_two.chat_client;

import ru.java_two.chat_library.Protocol;
import ru.java_two.network.SocketThread;
import ru.java_two.network.SocketThreadListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class ClientGUI extends JFrame implements ActionListener,
        Thread.UncaughtExceptionHandler, SocketThreadListener {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    private static final String WINDOW_TITLE = "Chat Client";

    private final JTextArea log = new JTextArea();
    private final JPanel panelTop = new JPanel(new GridLayout(2, 3));
    private final JTextField tfIpAddress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("8189");
    private final JCheckBox cbAlwaysOnTop = new JCheckBox("Always on top");
    private final JTextField tfLogin = new JTextField("Aleksey");
    private final JPasswordField tfPassword = new JPasswordField("123");
    private final JButton btnLogin = new JButton("Login");
    private final DateFormat DATE_FORMAT = new SimpleDateFormat("[HH:mm] ");

    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JButton btnDisconnect = new JButton("Disconnect");
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");
    private final JList<String> usersList = new JList<>();
    private boolean shownIoErrors = false;
    private SocketThread socketThread;

    private JComboBox dropDownUsersList = new JComboBox<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientGUI();
            }
        });
    }

    private ClientGUI() {
        Thread.setDefaultUncaughtExceptionHandler(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        log.setEditable(false);
        log.setLineWrap(true);
        JScrollPane scrollLog = new JScrollPane(log);
        JScrollPane scrollUsers = new JScrollPane(usersList);
        scrollUsers.setPreferredSize(new Dimension(100, 0));
        panelTop.add(tfIpAddress);
        panelTop.add(tfPort);
        panelTop.add(cbAlwaysOnTop);
        panelTop.add(tfLogin);
        panelTop.add(tfPassword);
        panelTop.add(btnLogin);
        panelBottom.add(dropDownUsersList, BorderLayout.SOUTH);
        panelBottom.add(btnDisconnect, BorderLayout.WEST);
        panelBottom.add(tfMessage, BorderLayout.CENTER);
        panelBottom.add(btnSend, BorderLayout.EAST);
        panelBottom.setVisible(false);
        btnSend.addActionListener(this);
        cbAlwaysOnTop.addActionListener(this);
        tfMessage.addActionListener(this);
        btnLogin.addActionListener(this);
        btnDisconnect.addActionListener(this);
        setTitle(WINDOW_TITLE);
        add(scrollLog, BorderLayout.CENTER);
        add(panelTop, BorderLayout.NORTH);
        add(panelBottom, BorderLayout.SOUTH);
        add(scrollUsers, BorderLayout.EAST);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == cbAlwaysOnTop) {
            setAlwaysOnTop(cbAlwaysOnTop.isSelected());
        } else if (src == btnSend || src == tfMessage) {
            sendMessage();
        } else if (src == btnLogin) {
            connect();
        } else if (src == btnDisconnect) {
            disconnect();
        } else {
            throw new RuntimeException("Undefined source: " + src);
        }
    }

    private void showException(Thread thread, Throwable exception) {
        String message;
        StackTraceElement[] ste = exception.getStackTrace();
        if (ste.length == 0) {
            message = "Empty StackTrace";
        } else {
            message = String.format("Exception in thread \"%s\" %s: %s\n\tat %s",
                    thread.getName(), exception.getClass().getCanonicalName(),
                    exception.getMessage(), ste[0]);
            JOptionPane.showMessageDialog(this, message, "Exception", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable exception) {
        exception.printStackTrace();
        showException(thread, exception);
        System.exit(1);
    }

    /**
     * The method creates a new log file, in which after each message there is a record.
     * Created a file named LYearMonthDay.log (Example: L20220818.log)
     *
     * @throws IOException - file exceptions.
     */

    private void writingLogToFile(String msg, String username) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            String date = simpleDateFormat.format(new Date());
            BufferedWriter writeLog = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("L" + date + ".log"), StandardCharsets.UTF_8));
            writeLog.write(username + ": " + msg);
            writeLog.flush();
        } catch (IOException e) {
            if (!shownIoErrors) {
                shownIoErrors = true;
                showException(Thread.currentThread(), e);
            }
        }
    }

    private void putLog(String msg) {
        if ("".equals(msg)) return;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.append(msg + "\n");
                log.setCaretPosition(log.getDocument().getLength());
            }
        });
    }

    /**
     * Method for printing messages to the log.
     */

    private void sendMessage() {
        String message = tfMessage.getText();
        if (message.equals("") || message.trim().length() == 0) return;
        tfMessage.setText(null);
        tfMessage.grabFocus();
        socketThread.sendMessage(Protocol.getUserBroadcast(message));
    }

    public void connect() {
        try {
            Socket socket = new Socket(tfIpAddress.getText(), Integer.parseInt(tfPort.getText()));
            socketThread = new SocketThread(this, "Client", socket);
        } catch (IOException e) {
            showException(Thread.currentThread(), e);
        }
    }

    private void disconnect() {
        socketThread.close();
    }

    @Override
    public void onSocketStart(SocketThread thread, Socket socket) {
        putLog("Socket started");
    }

    @Override
    public void onSocketStop(SocketThread thread) {
        panelTop.setVisible(true);
        panelBottom.setVisible(false);
        putLog("Socket stopped");
        setTitle(WINDOW_TITLE);
        usersList.setListData(new String[0]);
    }

    @Override
    public void onSocketReady(SocketThread thread, Socket socket) {
        putLog("Socket is ready");
        socketThread.sendMessage(Protocol.getAuthRequest(tfLogin.getText(), new String(tfPassword.getPassword())));
    }

    @Override
    public void onReceiveString(SocketThread thread, Socket socket, String message) {
        handleMessage(message);
    }

    private void handleMessage(String message) {
        String[] arrayUserData = message.split(Protocol.DELIMITER);
        String msgType = arrayUserData[0];
        switch (msgType) {
            case Protocol.AUTH_ACCEPT -> {
                setTitle(WINDOW_TITLE + " nickname: " + arrayUserData[1]);
                panelTop.setVisible(false);
                panelBottom.setVisible(true);
            }
            case Protocol.AUTH_DENIED -> putLog("Authorization failed");
            case Protocol.MSG_FORMAT_ERROR -> {
                putLog(message);
                socketThread.close();
            }
            case Protocol.TYPE_BROADCAST ->
                    putLog(String.format("%s %s: %s", DATE_FORMAT.format(Long.parseLong(arrayUserData[1])), arrayUserData[2], arrayUserData[3]));
            case Protocol.USER_LIST -> {
                String users = message.substring(Protocol.USER_LIST.length() + Protocol.DELIMITER.length());
                String[] usersArray = users.split(Protocol.DELIMITER);
                Arrays.sort(usersArray);
                usersList.setListData(usersArray);
                dropDownUsersList.setModel(new DefaultComboBoxModel(usersArray));
                dropDownUsersList.addItem("Everybody");
                dropDownUsersList.setSelectedItem("Everybody");
            }
            default -> throw new RuntimeException("Unknown message type");
        }
    }

    @Override
    public void onSocketException(SocketThread thread, Exception exception) {
        exception.printStackTrace();
    }
}
