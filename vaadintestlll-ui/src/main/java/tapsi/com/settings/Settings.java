package tapsi.com.settings;

import com.vaadin.navigator.View;
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

    private Button btn1;
    private Button savePassword;

    private PasswordField password;
    private PasswordField passwordCompare;

    public Settings() {
        buildView();
    }

    private void buildView() {
        setSizeFull();
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
        panel1 = new Panel("Connection");
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Image image1 = new Image(null, new ThemeResource("img/ic_launcher.png"));
        btn1 = new Button("Restart Server");

        horizontalLayout.setStyleName("panel_layout");
        image1.setStyleName("panel_image");
        image1.setHeight("100%");
        horizontalLayout.setSizeFull();
        horizontalLayout.setMargin(true);
        horizontalLayout.addComponents(image1, btn1);
        horizontalLayout.setExpandRatio(btn1, 1);
        horizontalLayout.setComponentAlignment(image1, Alignment.MIDDLE_LEFT);
        horizontalLayout.setComponentAlignment(btn1, Alignment.MIDDLE_CENTER);
        panel1.setSizeFull();
        panel1.setContent(horizontalLayout);

        btn1.addClickListener(click -> btn1OnClick());

        firstRow.addComponent(panel1);


        // Second row
        HorizontalLayout secondRow = new HorizontalLayout();
        secondRow.setSizeFull();

        // Second Row Items
        // 1. Panel

        panel2 = new Panel("Admin Password");
        HorizontalLayout horizontalLayout1 = new HorizontalLayout();
        Image image2 = new Image(null, new ThemeResource("img/ic_launcher.png"));
        password = new PasswordField("Password");
        passwordCompare = new PasswordField("Repeat Password");
        savePassword = new Button("Save Password");

        horizontalLayout1.setStyleName("panel_layout");
        image2.setStyleName("panel_image");
        image2.setHeight("100%");
        horizontalLayout1.setSizeFull();
        horizontalLayout1.setMargin(true);
        password.setSizeFull();
        passwordCompare.setSizeFull();
        horizontalLayout1.addComponents(image2, password, passwordCompare, savePassword);
        //horizontalLayout1.setExpandRatio(savePassword, 1);
        horizontalLayout1.setComponentAlignment(image2, Alignment.MIDDLE_LEFT);
        horizontalLayout1.setComponentAlignment(password, Alignment.MIDDLE_LEFT);
        horizontalLayout1.setComponentAlignment(passwordCompare, Alignment.MIDDLE_LEFT);
        horizontalLayout1.setComponentAlignment(savePassword, Alignment.MIDDLE_CENTER);
        horizontalLayout1.setExpandRatio(image2, 0.1f);
        horizontalLayout1.setExpandRatio(password, 0.3f);
        horizontalLayout1.setExpandRatio(passwordCompare, 0.3f);
        horizontalLayout1.setExpandRatio(savePassword, 0.3f);
        panel2.setSizeFull();
        panel2.setContent(horizontalLayout1);

        savePassword.addClickListener(click -> savePasswordOnClick());

        secondRow.addComponent(panel2);

        // Spacer from the bottom
        HorizontalLayout spacer = new HorizontalLayout();
        spacer.setSizeFull();

        setMargin(false);
        addComponents(pageLayout, firstRow, secondRow, spacer);
        setExpandRatio(pageLayout, 0.1f);
        setExpandRatio(firstRow, 0.2f);
        setExpandRatio(secondRow, 0.2f);
        setExpandRatio(spacer, 0.5f);
    }

    public void btn1OnClick() {
        SocketThread.sendMessage("server:restart:GeoDoorVisu");
    }

    public void savePasswordOnClick() {
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

    private void showPassNotification(String message) {
        Notification notification = new Notification("Error!",
                message,
                Notification.Type.HUMANIZED_MESSAGE);
        notification.setDelayMsec(2000);
        notification.show(Page.getCurrent());
    }
}
