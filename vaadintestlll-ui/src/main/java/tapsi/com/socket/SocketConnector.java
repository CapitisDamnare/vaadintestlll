package tapsi.com.socket;

import tapsi.com.data.Client;
import tapsi.com.data.DataHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public final class SocketConnector implements Serializable {

    private static Boolean connectionStatus = false;

    private static int serverPort;
    private static String serverIPAddress;

    private static Socket socket = null;

    private static ObjectInputStream objectInputStream = null;
    private static ObjectOutputStream objectOutputStream = null;

    public SocketConnector(int serverPort, String serverIPAddress) {
        this.serverPort = serverPort;
        this.serverIPAddress = serverIPAddress;
    }

    public static String getConnectionStatus() {
        if (connectionStatus)
            return "Connected";
        else
            return "Disconnected";
    }

    public static boolean initConnection() {
        try {
            InetAddress serverAddr = InetAddress.getByName(serverIPAddress);
            socket = new Socket(serverAddr, serverPort);
            socket.setSoTimeout(10000);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String sendMessage(String msg, String serialNumber) {
        connectionStatus = false;
        String socketinputObject;
        String tempMsg = msg + "-" + serialNumber;
        String socketoutputObject = new String(tempMsg + "!");

        if (!initConnection())
            return null;

        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(socketoutputObject);
            objectOutputStream.flush();
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        try {
            while ((socketinputObject = (String) objectInputStream.readObject()) != null) {
                String messageTemp = socketinputObject;
                System.out.println("orig:" + messageTemp);
                String command = messageTemp.substring(0, messageTemp.indexOf("!"));
                messageTemp = messageTemp.replace(command + "!", "");

                String value = messageTemp;

                switch (command) {
                    case "answer:clients":
                        saveAll(value);
                        break;
                    case "answer:log":
                        System.out.println("log: " + value);
                        break;
                }
                connectionStatus = true;
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        } finally {
            if (socket != null)
                try {
                    socket.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                }
        }
        return "";
    }

    public String sendUpdate(String msg, String serialNumber) {
        connectionStatus = false;
        String socketinputObject;
        String tempMsg = msg + "-" + serialNumber;
        String socketoutputObject = new String(tempMsg + "!" + XMLWriter.getXml());

        if (!initConnection())
            return null;

        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(socketoutputObject);
            objectOutputStream.flush();
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        try {
            while ((socketinputObject = (String) objectInputStream.readObject()) != null) {
                String messageTemp = socketinputObject;
                String command = messageTemp.substring(0, messageTemp.indexOf("!"));

                if (command.equals("true"))
                    connectionStatus = true;
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        } finally {
            if (socket != null)
                try {
                    socket.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                }
        }
        return "";
    }

    public static void saveAll(String value) {
        List<Client> users = new ArrayList<>();
        users = XMLReader.readConfig(value);
        DataHandler.setUsers(users);
    }
}
