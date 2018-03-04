package tapsi.com.settings;

import com.vaadin.navigator.View;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
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

        // Spacer fro the bottom
        HorizontalLayout spacer = new HorizontalLayout();
        spacer.setSizeFull();

        setMargin(false);
        addComponents(pageLayout, firstRow, spacer);
        setExpandRatio(pageLayout,0.1f);
        setExpandRatio(firstRow, 0.2f);
        setExpandRatio(spacer, 0.7f);
    }

    public void btn1OnClick() {
        SocketThread.sendMessage("server:restart:GeoDoorVisu");
    }
}
