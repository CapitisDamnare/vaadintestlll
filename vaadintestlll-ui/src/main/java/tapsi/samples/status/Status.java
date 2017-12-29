package tapsi.samples.status;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import tapsi.samples.socket.SocketThread;

public class Status extends HorizontalLayout implements View {

    public static final String VIEW_NAME = "Status";
    private Button sendMessage;

    public Status() {
        sendMessage = new Button("Send Message");
        sendMessage.addClickListener(e -> sendMsgBtnClick());

        addComponents(new Label("Hier kommt die Statuspage"),sendMessage);

    }

    public void sendMsgBtnClick() {
        SocketThread.sendMessage("server:GeoDoorVisu");
    }
}
