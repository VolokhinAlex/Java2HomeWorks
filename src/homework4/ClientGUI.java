package homework4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientGUI extends JFrame implements ActionListener,
        Thread.UncaughtExceptionHandler {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private final JTextArea log = new JTextArea();
    private final JPanel panelTop = new JPanel(new GridLayout(2,3));
    private final JTextField tfIpAddress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("1111");
    private final JCheckBox cbAlwaysOnTop = new JCheckBox("Always on top");
    private final JTextField tfLogin = new JTextField("Aleksey");
    private final JPasswordField tfPassword = new JPasswordField("123");
    private final JButton btnLogin = new JButton("Login");

    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JButton btnDisconnect = new JButton("Disconnect");
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");
    private final JList<String> usersList = new JList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientGUI();
            }
        });
    }

    private ClientGUI() {
        Thread.setDefaultUncaughtExceptionHandler(this); // Обрабатывает исключения
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH,HEIGHT);
        setLocationRelativeTo(null);
        log.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(log);
        JScrollPane scrollUsers = new JScrollPane(usersList);
        String[] users = {"user1", "user2", "user3", "user4"};

        usersList.setListData(users);
        usersList.setPreferredSize(new Dimension(100, 0));
        panelTop.add(tfIpAddress);
        panelTop.add(tfPort);
        panelTop.add(cbAlwaysOnTop);
        panelTop.add(tfLogin);
        panelTop.add(tfPassword);
        panelTop.add(btnLogin);
        panelBottom.add(btnDisconnect, BorderLayout.WEST);
        panelBottom.add(tfMessage, BorderLayout.CENTER);
        panelBottom.add(btnSend, BorderLayout.EAST);

        btnSend.addActionListener(this);
        cbAlwaysOnTop.addActionListener(this);
        tfMessage.addActionListener(this);

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
            log.append(tfMessage.getText() + "\n");
            handlingRecordExclusion();
        }  else {
            throw new RuntimeException("Undefined source: " + src);
        }
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        StackTraceElement[] ste = e.getStackTrace();
        String errorMessage = String.format("Exception in thread \"%s\" %s: %s\n\tat %s",
                t.getName(),e.getClass().getCanonicalName(), e.getMessage(), ste[0]);
        JOptionPane.showMessageDialog(this, errorMessage, "Exception", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }

    /**
     * The method creates a new log file, in which after each message there is a record.
     * Created a file named LYearMonthDay.log (Example: L20220818.log)
     * @throws IOException - file exceptions.
     */

    private void writingLogToFile() throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = simpleDateFormat.format(new Date());
        BufferedWriter writeLog = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("L" + date + ".log"), StandardCharsets.UTF_8));
        writeLog.write(log.getText());
        writeLog.close();
    }

    /**
     * Method for handling an exception when creating and writing a file
     */

    private void handlingRecordExclusion() {
        try {
            writingLogToFile();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


}
