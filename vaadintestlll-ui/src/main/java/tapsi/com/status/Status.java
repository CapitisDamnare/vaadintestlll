package tapsi.com.status;

import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import tapsi.com.data.DataHandler;
import tapsi.com.socket.SocketConnector;
import tapsi.com.socket.SocketThread;

import java.awt.*;

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
        SocketThread.sendMessage("server:GeoDoorVisu");

        buildView();
        updateValues();
    }

    private void buildView() {
        System.out.println("build is called!");
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
        panel1 = new Panel("Connection");
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Image image1 = new Image(null, new ThemeResource("img/ic_launcher.png"));
        panelLabel1 = new Label("This is my panel");

        horizontalLayout.setStyleName("panel_layout");
        panelLabel1.setStyleName("panel_label");
        image1.setStyleName("panel_image");
        image1.setHeight("100%");
        horizontalLayout.setSizeFull();
        horizontalLayout.setMargin(true);
        horizontalLayout.addComponents(image1, panelLabel1);
        horizontalLayout.setExpandRatio(panelLabel1,1);
        horizontalLayout.setComponentAlignment(image1,Alignment.MIDDLE_LEFT);
        horizontalLayout.setComponentAlignment(panelLabel1,Alignment.MIDDLE_CENTER);
        panel1.setSizeFull();
        panel1.setContent(horizontalLayout);

        // 2. Panel
        panel2 = new Panel("Last connected");
        HorizontalLayout horizontalLayout2 = new HorizontalLayout();
        Image image2 = new Image(null, new ThemeResource("img/ic_launcher.png"));
        panelLabel2 = new Label("This is my panel");

        horizontalLayout2.setStyleName("panel_layout");
        panelLabel2.setStyleName("panel_label");
        image2.setStyleName("panel_image");
        image2.setHeight("100%");
        horizontalLayout2.setSizeFull();
        horizontalLayout2.setMargin(true);
        horizontalLayout2.addComponents(image2, panelLabel2);
        horizontalLayout2.setExpandRatio(panelLabel2,1);
        horizontalLayout2.setComponentAlignment(image2,Alignment.MIDDLE_LEFT);
        horizontalLayout2.setComponentAlignment(panelLabel2,Alignment.MIDDLE_CENTER);
        panel2.setSizeFull();
        panel2.setContent(horizontalLayout2);

        // 3. Panel
        panel3 = new Panel("User allowed");
        HorizontalLayout horizontalLayout3 = new HorizontalLayout();
        Image image3 = new Image(null, new ThemeResource("img/ic_launcher.png"));
        panelLabel3 = new Label("This is my panel");

        horizontalLayout3.setStyleName("panel_layout");
        panelLabel3.setStyleName("panel_label");
        image3.setStyleName("panel_image");
        image3.setHeight("100%");
        horizontalLayout3.setSizeFull();
        horizontalLayout3.setMargin(true);
        horizontalLayout3.addComponents(image3, panelLabel3);
        horizontalLayout3.setExpandRatio(panelLabel3,1);
        horizontalLayout3.setComponentAlignment(image3,Alignment.MIDDLE_LEFT);
        horizontalLayout3.setComponentAlignment(panelLabel3,Alignment.MIDDLE_CENTER);
        panel3.setSizeFull();
        panel3.setContent(horizontalLayout3);

        firstRow.addComponents(panel1, panel2, panel3);


        // Second Row Items
        // 4. Panel
        panel4 = new Panel("User registered");
        HorizontalLayout horizontalLayout4 = new HorizontalLayout();
        Image image4 = new Image(null, new ThemeResource("img/ic_launcher.png"));
        panelLabel4 = new Label("This is my panel");

        horizontalLayout4.setStyleName("panel_layout");
        panelLabel4.setStyleName("panel_label");
        image4.setStyleName("panel_image");
        image4.setHeight("100%");
        horizontalLayout4.setSizeFull();
        horizontalLayout4.setMargin(true);
        horizontalLayout4.addComponents(image4, panelLabel4);
        horizontalLayout4.setExpandRatio(panelLabel4,1);
        horizontalLayout4.setComponentAlignment(image4,Alignment.MIDDLE_LEFT);
        horizontalLayout4.setComponentAlignment(panelLabel4,Alignment.MIDDLE_CENTER);
        panel4.setSizeFull();
        panel4.setContent(horizontalLayout4);

        // 5. Panel
        panel5 = new Panel("Online Time");
        HorizontalLayout horizontalLayout5 = new HorizontalLayout();
        Image image5 = new Image(null, new ThemeResource("img/ic_launcher.png"));
        panelLabel5 = new Label("This is my panel");

        horizontalLayout5.setStyleName("panel_layout");
        panelLabel5.setStyleName("panel_label");
        image5.setStyleName("panel_image");
        image5.setHeight("100%");
        horizontalLayout5.setSizeFull();
        horizontalLayout5.setMargin(true);
        horizontalLayout5.addComponents(image5, panelLabel5);
        horizontalLayout5.setExpandRatio(panelLabel5,1);
        horizontalLayout5.setComponentAlignment(image5,Alignment.MIDDLE_LEFT);
        horizontalLayout5.setComponentAlignment(panelLabel5,Alignment.MIDDLE_CENTER);
        panel5.setSizeFull();
        panel5.setContent(horizontalLayout5);

        // 6. Panel
        panel6 = new Panel("Time since restart");
        HorizontalLayout horizontalLayout6 = new HorizontalLayout();
        Image image6 = new Image(null, new ThemeResource("img/ic_launcher.png"));
        panelLabel6 = new Label("This is my panel");

        horizontalLayout6.setStyleName("panel_layout");
        panelLabel6.setStyleName("panel_label");
        image6.setStyleName("panel_image");
        image6.setHeight("100%");
        horizontalLayout6.setSizeFull();
        horizontalLayout6.setMargin(true);
        horizontalLayout6.addComponents(image6, panelLabel6);
        horizontalLayout6.setExpandRatio(panelLabel6,1);
        horizontalLayout6.setComponentAlignment(image6,Alignment.MIDDLE_LEFT);
        horizontalLayout6.setComponentAlignment(panelLabel6,Alignment.MIDDLE_CENTER);
        panel6.setSizeFull();
        panel6.setContent(horizontalLayout6);

        secondRow.addComponents(panel4, panel5, panel6);

        // Log Text Area
        log = new TextArea("Last Messages");
        log.setValue("This are the servers last Messsages");
        log.setStyleName("status_log");
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
        setExpandRatio(log,0.45f);
        setExpandRatio(spacer,0.05f);
    }

    private void updateValues() {
        panelLabel1.setValue(SocketConnector.getConnectionStatus());
        if (panelLabel1.getValue().equals("Connected")) {
            panelLabel1.setStyleName("panel_label_green");
        }
        else
            panelLabel1.setStyleName("panel_label");
        panelLabel2.setValue(DataHandler.lastConnected());
        panelLabel3.setValue(Integer.toString(DataHandler.userAllowed()));
        panelLabel4.setValue(Integer.toString(DataHandler.userCount()));
        System.out.println(DataHandler.getUsers().size());
    }

    public void sendMsgBtnClick() {
        SocketThread.sendMessage("server:GeoDoorVisu");
        updateValues();
    }
}
