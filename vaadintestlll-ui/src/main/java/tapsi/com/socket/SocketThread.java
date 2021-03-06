package tapsi.com.socket;

public class SocketThread {

    // TODO: Address changeable in the frontend?
    private static int serverPort = 5678;
    private static String serverIPAddress = "127.0.0.1";
    private static final String serverID = "13579";
    private static int serverUserPort = 1234;

    public SocketThread() {
        ObserverHandler observerHandler = new ObserverHandler();
    }

    public static int getServerPort() {
        return serverPort;
    }

    public static void setServerPort(int serverPort) {
        SocketThread.serverPort = serverPort;
    }

    public static String getServerIPAddress() {
        return serverIPAddress;
    }

    public static void setServerIPAddress(String serverIPAddress) {
        SocketThread.serverIPAddress = serverIPAddress;
    }

    public static int getServerUserPort() {
        return serverUserPort;
    }

    public static void setServerUserPort(int serverUserPort) {
        SocketThread.serverUserPort = serverUserPort;
    }

    public synchronized static void sendMessage(String message) {
        new Thread(() -> {
            SocketConnector sConnector = new SocketConnector(serverPort, serverIPAddress);
            String answer = sConnector.sendMessage(message, serverID);
            if (answer.equals("")) {
                System.out.println("sendMessage Timeout");
            }
        }).start();
    }

    public synchronized static void sendUpdate(String message) {
        new Thread(() -> {
            SocketConnector sConnector = new SocketConnector(serverPort, serverIPAddress);
            String answer = sConnector.sendUpdate(message, serverID);
            if (answer.equals("")) {
                System.out.println("sendUpdate Socket Timeout");
            }
        }).start();
    }

    public synchronized static void register(String message) {
        new  Thread(() -> {
            SocketConnector sConnector = new SocketConnector(serverPort, serverIPAddress);
            String answer = sConnector.sendMessage(message, serverID);
            if (answer.equals("")) {
                System.out.println("Register Socket Timeout");
            }
        }).start();
    }
}
