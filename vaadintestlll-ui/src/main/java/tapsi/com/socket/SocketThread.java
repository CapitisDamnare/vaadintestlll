package tapsi.com.socket;

public class SocketThread {

    // Socket Connection values
    private static SocketConnector sConnector;
    private static int serverPort = 9999;
    private static String serverIPAddress = "127.0.0.1";
    private static final String serverID = "13579";
    private static Boolean registered = false;

    public SocketThread() {
        //register("visuRegister:GeoDoorVisu");
    }

    public static void sendMessage(String message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sConnector = new SocketConnector(serverPort, serverIPAddress);
                    String answer = sConnector.sendMessage(message, serverID);
                    if (answer != null) {
                        System.out.println("Message: " + answer);
                    } else {
                        //System.out.println("Socket Timeout");
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        }).start();
    }

    public static void sendUpdate(String message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sConnector = new SocketConnector(serverPort, serverIPAddress);
                    String answer = sConnector.sendUpdate(message, serverID);
                    if (answer != null) {
                        System.out.println("Message: " + answer);
                    } else {
                        //System.out.println("Socket Timeout");
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        }).start();
    }

    public static void register(String message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sConnector = new SocketConnector(serverPort, serverIPAddress);
                    String answer = sConnector.sendMessage(message, serverID);
                    if (answer != null) {
                        if (answer.equals("allowed")) {
                            registered = true;
                        }
                        System.out.println("Register: " + answer);
                    } else {
                        System.out.println("Register Socket Timeout");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
