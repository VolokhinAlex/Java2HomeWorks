package homework4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerGUI extends JFrame implements ActionListener, Thread.UncaughtExceptionHandler{

    private static final int POS_X = 1000;
    private static final int POS_Y = 550;
    private static final int WIDTH = 200;
    private static final int HEIGHT = 100;

    private final ChatServer chatServer = new ChatServer();
    private final JButton btnStart = new JButton("Start");
    private final JButton btnStop = new JButton("Stop");

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() { // Event Dispatching Thread
            @Override
            public void run() {
                new ServerGUI(); // Event Queue
            }
        }); // Вызвать позже(Новый поток) Кладем в очередь событий

    }

    private ServerGUI() {
        Thread.setDefaultUncaughtExceptionHandler(this); // Обрабатывает исключения
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat Server");
        setLayout(new GridLayout(1,2));
        btnStart.addActionListener(this);
        btnStop.addActionListener(this);
        add(btnStart);
        add(btnStop);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == btnStart) {
            chatServer.start(1111);
        } else if (src == btnStop) {
            chatServer.stop();
        } else {
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
}
