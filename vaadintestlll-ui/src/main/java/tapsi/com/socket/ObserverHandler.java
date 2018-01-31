package tapsi.com.socket;

import java.util.ArrayList;
import java.util.List;

public class ObserverHandler {

    private static List<SocketListener> listeners = new ArrayList<>();

    public interface SocketListener {
        void onLogUpdate(String string);
        void onClientUpdate();
        void onAllowed();
        void onConnected();
        void onDisconnected();
    }

    public static void addCustomListener(SocketListener listener) {
        listeners.add(listener);
    }

    public static void onLogUpdate(String string) {
        for (SocketListener socketListener : listeners)
            socketListener.onLogUpdate(string);
    }

    public static void onClientUpdate() {
        for (SocketListener socketListener : listeners)
            socketListener.onClientUpdate();
    }

    public static void onAllowed() {
        for (SocketListener socketListener : listeners)
            socketListener.onAllowed();
    }

    public static void onConnected() {
        for (SocketListener socketListener : listeners)
            socketListener.onConnected();
    }

    public static void onDisconnected() {
        for (SocketListener socketListener : listeners)
            socketListener.onDisconnected();
    }
}
