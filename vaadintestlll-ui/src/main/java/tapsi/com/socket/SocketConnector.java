package tapsi.com.socket;

import tapsi.com.data.Client;
import tapsi.com.data.DataHandler;
import tapsi.com.data.LogHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;


public class SocketConnector implements Serializable {

    private int serverPort;
    private String serverIPAddress;

    private Socket socket = null;

    private ObjectInputStream objectInputStream = null;
    private ObjectOutputStream objectOutputStream = null;

    public SocketConnector(int serverPort, String serverIPAddress) {
        this.serverPort = serverPort;
        this.serverIPAddress = serverIPAddress;
    }

    public boolean initConnection() {
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

    public String sendMessage(String msg, String serialNumber) {
        ObserverHandler.onDisconnected();
        String answer = "";
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
                String command = messageTemp.substring(0, messageTemp.indexOf("!"));
                messageTemp = messageTemp.replace(command + "!", "");

                String value = messageTemp;
                command = command.replace("answer:", "");

                switch (command) {
                    case "clients":
                        saveAll(value);
                        ObserverHandler.onClientUpdate();
                        break;
                    case "log":
                        LogHandler.setData(value);
                        ObserverHandler.onLogUpdate(value);
                        break;
                    case "allowed":
                        ObserverHandler.onAllowed();
                        break;
                }
                ObserverHandler.onConnected();
                answer = command;
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return answer;
        } finally {
            if (socket != null)
                try {
                    socket.close();
                } catch (IOException e) {
                    ObserverHandler.onDisconnected();
                    //e.printStackTrace();
                }
        }
        return answer;
    }

    public String sendUpdate(String msg, String serialNumber) {
        ObserverHandler.onDisconnected();
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

                ObserverHandler.onConnected();
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

    public void saveAll(String value) {
        List<Client> users;
        users = XMLReader.readConfig(value);
        DataHandler.setUsers(users);
    }
}
