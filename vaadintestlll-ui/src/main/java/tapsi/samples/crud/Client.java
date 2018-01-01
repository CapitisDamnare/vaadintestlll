package tapsi.samples.crud;

import tapsi.samples.socket.SocketConnector;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.ListIterator;

public class Client implements Serializable {

    @NotNull
    private int id = -1;
    @NotNull
    private String name = "";
    @NotNull
    private String phoneID = "";
    @NotNull
    private String threadID = "";
    @NotNull
    private Allowance allowed = Allowance.NOTALLOWED;
    @NotNull
    private String lastConnection = "";

    public Client() {
    }

    public Client(Integer id, String name, String phoneID, String threadID, Allowance allowed, String lastConnection) {
        this.id = id;
        this.name = name;
        this.phoneID = phoneID;
        this.threadID = threadID;
        this.allowed = allowed;
        this.lastConnection = lastConnection;
    }

    public Client(List<String> data) {
        ListIterator<String> iterator = data.listIterator();
        this.id = new Integer((String) iterator.next());
        this.name = (String) iterator.next();
        this.phoneID = (String) iterator.next();
        this.threadID = (String) iterator.next();

        if (new Integer(iterator.next()).equals(1))
            this.allowed = Allowance.ALLOWED;
        else
            this.allowed = Allowance.NOTALLOWED;

        this.lastConnection = (String) iterator.next();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneID() {
        return phoneID;
    }

    public void setPhoneID(String phoneID) {
        this.phoneID = phoneID;
    }

    public String getThreadID() {
        return threadID;
    }

    public void setThreadID(String threadID) {
        this.threadID = threadID;
    }

    public Allowance getAllowed() {
        return allowed;
    }

    public void setAllowed(Allowance allowed) {
        this.allowed = allowed;
    }

    public String getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(String lastConnection) {
        this.lastConnection = lastConnection;
    }

    @Override
    public String toString() {
        return name + " " + phoneID;
    }
}
