package tapsi.com.socket;

import javafx.util.Pair;
import tapsi.com.data.Client;
import tapsi.com.data.DataHandler;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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
        if(connectionStatus)
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
        Pair<String,String> socketinputObject;
        String tempMsg = msg + "-" + serialNumber;
        Pair<String,String> socketoutputObject = new Pair<> (tempMsg, "");

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
            while ((socketinputObject = (Pair<String,String>) objectInputStream.readObject()) != null) {
                saveAll(socketinputObject);
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

    public String sendUpdate (String msg, String serialNumber) {
        connectionStatus = false;
        Pair<String,String> socketinputObject;
        String tempMsg = msg + "-" + serialNumber;
        Pair<String,String> socketoutputObject = new Pair<> (tempMsg, XMLWriter.getXml());

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
            while ((socketinputObject = (Pair<String,String>) objectInputStream.readObject()) != null) {
                if (socketinputObject.getKey().equals("true"))
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

    public static void saveAll(Pair<String, String> msg) {
        DataHandler.setUsers(XMLReader.readConfig(msg.getValue()));
    }
}
