package server;

public class Server {
    private  static  final int PORT = 1234;
    public static void main(String[] args) {
        MultiServer server = new MultiServer();
        server.start(PORT);
    }
}
