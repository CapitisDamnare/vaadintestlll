package tapsi.samples.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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
