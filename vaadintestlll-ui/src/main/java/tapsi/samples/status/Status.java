package tapsi.samples.status;

import com.vaadin.navigator.View;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import tapsi.samples.socket.SocketThread;

public class Status extends VerticalLayout implements View {

    public static final String VIEW_NAME = "Status";
    private Label pageLabel;
    private Button sendMessage;

    private Panel panel1;
    private Label panelLabel;

    private Panel panel2;
    private Label panelLabel2;

    private Panel panel3;
    private Panel panel4;
    private Panel panel5;
    private Panel panel6;

    public Status() {
        setSizeFull();
        pageLabel = new Label("Status");
        pageLabel.setHeight("200%");

        sendMessage = new Button("Send Message");
        sendMessage.addClickListener(e -> sendMsgBtnClick());

        HorizontalLayout firstRow = new HorizontalLayout();
        firstRow.setSizeFull();

        HorizontalLayout secondRow = new HorizontalLayout();
        firstRow.setSizeFull();

        panel1 = new Panel("Connected to Server");
        panel1.setSizeFull();
        FormLayout layout1 = new FormLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setMargin(true);
        Image image1 = new Image(null, new ThemeResource("img/ic_launcher.png"));
        panelLabel = new Label("This is my panel");
        horizontalLayout.addComponents(image1, panelLabel);
        layout1.addComponents(horizontalLayout);
        //layout1.setSizeUndefined();
        layout1.setMargin(true);
        panel1.setContent(horizontalLayout);


        panel2 = new Panel("User connected");
        panel2.setSizeFull();
        FormLayout layout2 = new FormLayout();
        layout2.setMargin(true);
        HorizontalLayout horizontalLayout2 = new HorizontalLayout();
        horizontalLayout2.setMargin(true);
        Image image2 = new Image(null, new ThemeResource("img/ic_launcher.png"));
        panelLabel2 = new Label("This is my panel");
        horizontalLayout2.addComponents(image2, panelLabel2);
        layout2.addComponents(horizontalLayout2);
        //layout2.setSizeUndefined();
        layout2.setMargin(true);
        panel2.setContent(horizontalLayout2);

        firstRow.addComponents(panel1, panel2);

        HorizontalLayout horizontalLayout3 = new HorizontalLayout();
        horizontalLayout3.setSizeFull();



        addComponents(pageLabel,sendMessage,firstRow, horizontalLayout3);

        setExpandRatio(pageLabel,0.05f);
        setExpandRatio(sendMessage,0.05f);
        setExpandRatio(firstRow,0.4f);
        setExpandRatio(horizontalLayout3,0.5f);

    }

    public void sendMsgBtnClick() {
        SocketThread.sendMessage("server:GeoDoorVisu");
    }
}
