package tapsi.samples.socket;

import javafx.util.Pair;
import tapsi.samples.crud.Client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public final class SocketConnector implements Serializable {

    private static List<Client> users = new ArrayList<>();

    private static int serverPort;
    private static String serverIPAddress;

    private static Socket socket = null;

    private static ObjectInputStream objectInputStream = null;
    private static ObjectOutputStream objectOutputStream = null;

    public SocketConnector(int serverPort, String serverIPAddress) {
        this.serverPort = serverPort;
        this.serverIPAddress = serverIPAddress;
    }

    public static List<Client> getUsers() {
        return users;
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
        Pair<String,List<List<String>>> socketinputObject;
        List<String> client = new ArrayList<>();
        client.add("whatever");
        String tempMsg = msg + "-" + serialNumber;
        Pair<String,List<String>> socketoutputObject = new Pair<String,List<String>> (tempMsg, client);

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
            while ((socketinputObject = (Pair<String,List<List<String>>>) objectInputStream.readObject()) != null) {
                printAll(socketinputObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (socket != null)
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return "";
    }

    public static void printAll(Pair<String, List<List<String>>> msg) {
        //System.out.println(msg.getKey());
        users = new ArrayList<>();
        List<List<String>> clients = msg.getValue();
        ListIterator<List<String>> iterator = clients.listIterator();
        while (iterator.hasNext()) {
            List<String> client = iterator.next();
            Client user = new Client(client);
            users.add(user);
//            ListIterator<String> innerIterator = client.listIterator();
//            while (innerIterator.hasNext()) {
//                System.out.println(innerIterator.next());
//            }
        }
    }

    public static Client getProductByID(int clientID) {
        ListIterator<Client> iterator = users.listIterator();
        while (iterator.hasNext()) {
            Client client = iterator.next();
            if (client.getId() == clientID)
                return client;
        }
        System.out.println("Returned null!!!!");
        return null;
    }

    public static void save(Client newClient) {
        if (newClient.getId().equals(-1)) {
            addUser(newClient);
            return;
        }

        ListIterator<Client> iterator = users.listIterator();
        while (iterator.hasNext()) {
            Client client = iterator.next();
            if (client.getId().equals(newClient.getId())) {
                client.setId(newClient.getId());
                client.setName(newClient.getName());
                client.setPhoneID(newClient.getPhoneID());
                client.setThreadID(newClient.getThreadID());
                client.setAllowed(newClient.getAllowed());
                client.setLastConnection(newClient.getLastConnection());
                return;
            }
        }

    }

    public static void delete(Client newClient) {
        int index = 0;
        ListIterator<Client> iterator = users.listIterator();
        while (iterator.hasNext()) {
            Client client = iterator.next();
            if (client.getId().equals(newClient.getId())) {
                break;
            }
            else
                index++;
        }
        users.remove(index);
    }

    public static void addUser(Client client) {
        users.add(client);
    }
}
