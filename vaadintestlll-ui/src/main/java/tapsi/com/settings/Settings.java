package tapsi.com.settings;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import tapsi.MyUI;
import tapsi.com.socket.SocketThread;

public class Settings extends VerticalLayout implements View {

    public static final String VIEW_NAME = "Settings";
    private Label pageLabel;

    private Panel panel1;
    private Panel panel2;
    private Panel panel3;
    private Panel panel4;
    private Panel panel5;
    private Panel panel6;

    private Button btnSoftReset;
    private Button btnHardReset;
    private Button btnSavePassword;
    private Button btnSaveConnection;
    private Button btnSaveUserPort;

    private TextField txtServerIP;
    private TextField txtServerPort;
    private TextField txtServerVisuPort;
    private TextField txtLocalVisuPort;

    private PasswordField password;
    private PasswordField passwordCompare;

    public Settings() {
        buildView();
        fillData();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        fillData();
    }

    private void buildView() {
        HorizontalLayout pageLayout = new HorizontalLayout();
        pageLabel = new Label("Settings");
        pageLabel.setStyleName("page_label");

        pageLayout.setSizeFull();
        pageLayout.setStyleName("page_label_layout");
        pageLayout.setHeight("65px");
        pageLayout.setMargin(false);
        pageLayout.addComponents(pageLabel);
        pageLayout.setComponentAlignment(pageLabel, Alignment.MIDDLE_CENTER);

        // First row
        HorizontalLayout firstRow = new HorizontalLayout();
        firstRow.setSizeFull();

        // First Row Items
        // 1. Panel
        panel1 = new Panel("Restart Server");
        HorizontalLayout horizontalLayout1 = new HorizontalLayout();
        Image image1 = new Image(null, new ThemeResource("img/ic_launcher.png"));
        btnSoftReset = new Button("Soft Restart Server");
        btnSoftReset.setDescription("Restarts the Server Threads!");
        btnHardReset = new Button("Hard Restart Server");
        btnHardReset.setDescription("Uses a script to terminate and restart the process!");

        horizontalLayout1.setStyleName("panel_layout");
        image1.setStyleName("panel_image");
        image1.setHeight("100%");
        btnSoftReset.setSizeFull();
        btnHardReset.setSizeFull();
        horizontalLayout1.setSizeFull();
        horizontalLayout1.setMargin(true);
        horizontalLayout1.addComponents(image1, btnSoftReset, btnHardReset);
        horizontalLayout1.setExpandRatio(btnSoftReset, 0.5f);
        horizontalLayout1.setExpandRatio(btnHardReset, 0.5f);
        horizontalLayout1.setComponentAlignment(image1, Alignment.MIDDLE_LEFT);
        horizontalLayout1.setComponentAlignment(btnSoftReset, Alignment.MIDDLE_CENTER);
        horizontalLayout1.setComponentAlignment(btnHardReset, Alignment.MIDDLE_CENTER);
        panel1.setSizeFull();
        panel1.setContent(horizontalLayout1);

        btnSoftReset.addClickListener(click -> btnSoftResetClick());
        btnHardReset.addClickListener(click -> btnHardResetClick());

        firstRow.addComponent(panel1);

        // Second row
        HorizontalLayout secondRow = new HorizontalLayout();
        secondRow.setSizeFull();

        // Second Row Items
        // 2. Panel
        panel2 = new Panel("Admin Password");
        HorizontalLayout horizontalLayout2 = new HorizontalLayout();
        Image image2 = new Image(null, new ThemeResource("img/ic_launcher.png"));
        password = new PasswordField("Password");
        passwordCompare = new PasswordField("Repeat Password");
        btnSavePassword = new Button("Save Password");

        horizontalLayout2.setStyleName("panel_layout");
        image2.setStyleName("panel_image");
        image2.setHeight("100%");
        horizontalLayout2.setSizeFull();
        horizontalLayout2.setMargin(true);
        password.setSizeFull();
        passwordCompare.setSizeFull();
        btnSavePassword.setSizeFull();
        btnSavePassword.setHeight("100%");
        horizontalLayout2.addComponents(image2, password, passwordCompare, btnSavePassword);
        horizontalLayout2.setComponentAlignment(image2, Alignment.MIDDLE_LEFT);
        horizontalLayout2.setComponentAlignment(password, Alignment.MIDDLE_LEFT);
        horizontalLayout2.setComponentAlignment(passwordCompare, Alignment.MIDDLE_LEFT);
        horizontalLayout2.setComponentAlignment(btnSavePassword, Alignment.MIDDLE_CENTER);
        horizontalLayout2.setExpandRatio(password, 0.3f);
        horizontalLayout2.setExpandRatio(passwordCompare, 0.3f);
        horizontalLayout2.setExpandRatio(btnSavePassword, 0.3f);
        panel2.setSizeFull();
        panel2.setContent(horizontalLayout2);

        btnSavePassword.addClickListener(click -> savePasswordOnClick());

        secondRow.addComponent(panel2);

        // Third row
        HorizontalLayout thirdRow = new HorizontalLayout();
        thirdRow.setSizeFull();

        // Third Row Items
        // 3.Panel
        panel3 = new Panel("Visualization Connection Settings");
        HorizontalLayout horizontalLayout3 = new HorizontalLayout();
        Image image3 = new Image(null, new ThemeResource("img/ic_launcher.png"));
        VerticalLayout verticalLayout1 = new VerticalLayout();
        txtLocalVisuPort = new TextField("Local Visu PORT");
        txtServerVisuPort = new TextField("Server Visu PORT");
        VerticalLayout verticalLayout2 = new VerticalLayout();
        txtServerIP = new TextField("Server IP Address");
        btnSaveConnection = new Button("Save Connection");

        btnSaveConnection.addClickListener(click -> btnSaveConnectionOnClick());

        verticalLayout1.setSizeFull();
        verticalLayout1.setMargin(false);
        verticalLayout1.addComponents(txtLocalVisuPort, txtServerVisuPort);
        txtLocalVisuPort.setSizeFull();
        txtServerVisuPort.setSizeFull();
        verticalLayout2.setSizeFull();
        verticalLayout2.setMargin(false);
        verticalLayout2.addComponents(txtServerIP, btnSaveConnection);
        txtServerIP.setSizeFull();
        btnSaveConnection.setSizeFull();

        horizontalLayout3.setStyleName("panel_layout");
        image3.setStyleName("panel_image");
        image3.setHeight("100%");
        horizontalLayout3.setSizeFull();
        horizontalLayout3.setMargin(true);
        horizontalLayout3.addComponents(image3, verticalLayout1, verticalLayout2);
        horizontalLayout3.setComponentAlignment(image3, Alignment.MIDDLE_LEFT);
        horizontalLayout3.setExpandRatio(verticalLayout1, 0.5f);
        horizontalLayout3.setExpandRatio(verticalLayout2, 0.5f);
        panel3.setSizeFull();
        panel3.setContent(horizontalLayout3);

        thirdRow.addComponents(panel3);

        // Fourth row
        HorizontalLayout fourthRow = new HorizontalLayout();
        fourthRow.setSizeFull();

        // Fourth Row Items
        // 4.Panel
        panel4 = new Panel("User Server PORT Connection");
        HorizontalLayout horizontalLayout4 = new HorizontalLayout();
        Image image4 = new Image(null, new ThemeResource("img/ic_launcher.png"));
        txtServerPort = new TextField("Server PORT");
        btnSaveUserPort = new Button("Save User PORT");

        btnSaveUserPort.addClickListener(click -> btnSaveUserPortOnClick());

        txtServerPort.setSizeFull();
        btnSaveUserPort.setSizeFull();

        horizontalLayout4.setStyleName("panel_layout");
        image4.setStyleName("panel_image");
        image4.setHeight("100%");
        horizontalLayout4.setSizeFull();
        horizontalLayout4.setMargin(true);
        horizontalLayout4.addComponents(image4, txtServerPort, btnSaveUserPort);
        horizontalLayout4.setExpandRatio(txtServerPort, 0.5f);
        horizontalLayout4.setExpandRatio(btnSaveUserPort, 0.5f);
        horizontalLayout4.setComponentAlignment(image4, Alignment.MIDDLE_LEFT);
        horizontalLayout4.setComponentAlignment(txtServerPort, Alignment.MIDDLE_CENTER);
        horizontalLayout4.setComponentAlignment(btnSaveUserPort, Alignment.MIDDLE_CENTER);

        panel4.setSizeFull();
        panel4.setContent(horizontalLayout4);

        fourthRow.addComponents(panel4);

        // Spacer from the bottom
        HorizontalLayout spacer = new HorizontalLayout();
        spacer.setSizeFull();

        setMargin(false);
        addComponents(pageLayout, firstRow, secondRow, thirdRow, fourthRow, spacer);
        setExpandRatio(pageLayout, 0.1f);
        setExpandRatio(firstRow, 0.2f);
        setExpandRatio(secondRow, 0.2f);
        setExpandRatio(thirdRow, 0.2f);
        setExpandRatio(fourthRow, 0.2f);
        setExpandRatio(spacer, 0.1f);
    }

    private void fillData() {
        txtLocalVisuPort.setValue(Integer.toString(SocketThread.getServerPort()));
        txtServerVisuPort.setValue(Integer.toString(SocketThread.getServerPort()));
        txtServerIP.setValue(SocketThread.getServerIPAddress());
        txtServerPort.setValue(Integer.toString(SocketThread.getServerUserPort()));
    }

    private void btnSoftResetClick() {
        SocketThread.sendMessage("server:restart:GeoDoorVisu");
    }

    private void btnHardResetClick() {

    }

    private void savePasswordOnClick() {
        if (!password.isEmpty() && !passwordCompare.isEmpty())
            if (password.getValue().equals(passwordCompare.getValue())) {
                MyUI.get().getAccessControl().safeAdminPassword(password.getValue());
                password.setValue("");
                passwordCompare.setValue("");
            } else {
                showPassNotification("Passwords don't match!");
            }
        else {
            showPassNotification("Empty fields are not allowed!");
        }
    }

    private void btnSaveConnectionOnClick() {
        if (txtLocalVisuPort.isEmpty() || txtServerVisuPort.isEmpty()) {
            showPassNotification("PORT fields can't be empty!");
            return;
        }
        if (txtServerIP.isEmpty()) {
            showPassNotification("Server IP can't be empty!");
            return;
        }
        if (!txtServerVisuPort.getValue().equals(txtLocalVisuPort.getValue())) {
            showPassNotification("Local Visu PORT and Server Visu PORT must have the same PORT");
            return;
        }

        SocketThread.setServerIPAddress(txtServerIP.getValue());
        try {
            int testPort = Integer.parseInt(txtLocalVisuPort.getValue());
        } catch (Exception e) {
            showPassNotification("Wrong PORT Number!");
            return;
        }
        SocketThread.sendMessage("server:visuPort@" + txtServerVisuPort.getValue() + ":GeoDoorVisu");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SocketThread.setServerPort(Integer.parseInt(txtLocalVisuPort.getValue()));
    }

    private void btnSaveUserPortOnClick() {
        try {
            int testPort = Integer.parseInt(txtServerPort.getValue());
        } catch (Exception e) {
            showPassNotification("Wrong User PORT Number!");
            return;
        }
        SocketThread.setServerPort(Integer.parseInt(txtLocalVisuPort.getValue()));
        SocketThread.sendMessage("server:serverPort@" + txtServerVisuPort.getValue() + ":GeoDoorVisu");
    }

    private void showPassNotification(String message) {
        Notification notification = new Notification("Error!",
                message,
                Notification.Type.HUMANIZED_MESSAGE);
        notification.setDelayMsec(1000);
        notification.show(Page.getCurrent());
    }
}
