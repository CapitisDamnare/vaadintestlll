package tapsi.samples.data;

import tapsi.samples.user.Allowance;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataHandler implements Serializable {

    private static List<Client> users = new ArrayList<>();

    public static List<Client> getUsers() {
        return users;
    }

    public static void setUsers(List<Client> users) {
        DataHandler.users = users;
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
            } else
                index++;
        }
        users.remove(index);
    }

    public static void addUser(Client client) {
        users.add(client);
    }

    public static int userCount() {
        return users.size();
    }

    public static String lastConnected() {
        ListIterator iterator = users.listIterator();
        while (iterator.hasNext()) {
            DateFormat dateFormat = new SimpleDateFormat(
                    "EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            Client client = (Client) iterator.next();
            try {
                dateFormat.parse(client.getLastConnection());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println(dateFormat.format(new Date()));

        }
        return "";
    }

    public static int userAllowed() {
        int count = 0;
        ListIterator iterator = users.listIterator();
        while (iterator.hasNext()) {
            DateFormat dateFormat = new SimpleDateFormat(
                    "EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            Client client = (Client) iterator.next();
            if (client.getAllowed().equals(Allowance.ALLOWED))
                count++;

        }
        return count;
    }
}
