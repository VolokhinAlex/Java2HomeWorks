package homework4;

public class ChatServer {

    private int port;

    public void start(int port) {
        System.out.println("Server started at port " + port);
    }

    public void stop() {
        System.out.println("Server Stopped");
    }

}
