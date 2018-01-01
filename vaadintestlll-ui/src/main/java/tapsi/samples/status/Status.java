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
    private Panel panel2;
    private Panel panel3;
    private Panel panel4;
    private Panel panel5;
    private Panel panel6;

    private Label panelLabel1;
    private Label panelLabel2;
    private Label panelLabel3;
    private Label panelLabel4;
    private Label panelLabel5;
    private Label panelLabel6;

    private TextArea log;

    public Status() {
        setSizeFull();

        HorizontalLayout pageLayout = new HorizontalLayout();
        pageLabel = new Label("Status");
        pageLabel.setStyleName("page_label");

        pageLayout.setSizeFull();
        pageLayout.setStyleName("page_label_layout");
        pageLayout.setHeight("65px");
        pageLayout.setMargin(false);
        pageLayout.addComponents(pageLabel);
        pageLayout.setComponentAlignment(pageLabel,Alignment.MIDDLE_CENTER);

        HorizontalLayout btnLayout = new HorizontalLayout();
        btnLayout.setStyleName("btn_layout");
        sendMessage = new Button("Send Message");
        sendMessage.addClickListener(e -> sendMsgBtnClick());

        btnLayout.setMargin(false);
        btnLayout.setSizeFull();
        btnLayout.addComponent(sendMessage);
        //btnLayout.setComponentAlignment(sendMessage, Alignment.MIDDLE_LEFT);

        HorizontalLayout firstRow = new HorizontalLayout();
        firstRow.setSizeFull();

        HorizontalLayout secondRow = new HorizontalLayout();
        secondRow.setSizeFull();

        // First Row Items
        // 1. Panel
        panel1 = new Panel("Connected to Server");
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Image image1 = new Image(null, new ThemeResource("img/ic_launcher.png"));
        panelLabel1 = new Label("This is my panel");

        panelLabel1.setStyleName("panel_label");
        horizontalLayout.setMargin(true);
        horizontalLayout.addComponents(image1, panelLabel1);
        panel1.setSizeFull();
        panel1.setContent(horizontalLayout);

        // 2. Panel
        panel2 = new Panel("Last connected");
        HorizontalLayout horizontalLayout2 = new HorizontalLayout();
        Image image2 = new Image(null, new ThemeResource("img/ic_launcher.png"));
        panelLabel2 = new Label("This is my panel");

        panelLabel2.setStyleName("panel_label");
        horizontalLayout2.addComponents(image2, panelLabel2);
        horizontalLayout2.setMargin(true);
        panel2.setSizeFull();
        panel2.setContent(horizontalLayout2);

        // 3. Panel
        panel3 = new Panel("User allowed");
        HorizontalLayout horizontalLayout3 = new HorizontalLayout();
        Image image3 = new Image(null, new ThemeResource("img/ic_launcher.png"));
        panelLabel3 = new Label("This is my panel");

        panelLabel3.setStyleName("panel_label");
        horizontalLayout3.addComponents(image3, panelLabel3);
        horizontalLayout3.setMargin(true);
        panel3.setSizeFull();
        panel3.setContent(horizontalLayout3);

        firstRow.addComponents(panel1, panel2, panel3);


        // Second Row Items
        // 4. Panel
        panel4 = new Panel("User registered");
        HorizontalLayout horizontalLayout4 = new HorizontalLayout();
        Image image4 = new Image(null, new ThemeResource("img/ic_launcher.png"));
        panelLabel4 = new Label("This is my panel");

        panelLabel4.setStyleName("panel_label");
        horizontalLayout4.addComponents(image4, panelLabel4);
        horizontalLayout4.setMargin(true);
        panel4.setSizeFull();
        panel4.setContent(horizontalLayout4);

        // 5. Panel
        panel5 = new Panel("Online Time");
        HorizontalLayout horizontalLayout5 = new HorizontalLayout();
        Image image5 = new Image(null, new ThemeResource("img/ic_launcher.png"));
        panelLabel5 = new Label("This is my panel");

        panelLabel5.setStyleName("panel_label");
        horizontalLayout5.addComponents(image5, panelLabel5);
        horizontalLayout5.setMargin(true);
        panel5.setSizeFull();
        panel5.setContent(horizontalLayout5);

        // 6. Panel
        panel6 = new Panel("Time since restart");
        HorizontalLayout horizontalLayout6 = new HorizontalLayout();
        Image image6 = new Image(null, new ThemeResource("img/ic_launcher.png"));
        panelLabel6 = new Label("This is my panel");

        panelLabel6.setStyleName("panel_label");
        horizontalLayout6.addComponents(image6, panelLabel6);
        horizontalLayout6.setMargin(true);
        panel6.setSizeFull();
        panel6.setContent(horizontalLayout6);

        secondRow.addComponents(panel4, panel5, panel6);

        // Log Text Area
        log = new TextArea("Last Messages");
        log.setValue("This are the servers last Messsages");
        log.setSizeFull();
        log.setEnabled(false);

        // Spacer fro the bottom
        HorizontalLayout spacer = new HorizontalLayout();
        spacer.setSizeFull();

        addComponents(pageLayout,btnLayout,firstRow, secondRow, log, spacer);

        setMargin(false);
        setExpandRatio(pageLayout,0.05f);
        setExpandRatio(btnLayout,0.05f);
        setExpandRatio(firstRow,0.2f);
        setExpandRatio(secondRow,0.2f);
        setExpandRatio(log,0.3f);
        setExpandRatio(spacer,0.05f);
    }

    public void sendMsgBtnClick() {
        SocketThread.sendMessage("server:GeoDoorVisu");
    }
}
