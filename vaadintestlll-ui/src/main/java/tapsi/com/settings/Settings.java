package tapsi.com.settings;

import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class Settings extends VerticalLayout implements View {

    public static final String VIEW_NAME = "Settings";
    private Label pageLabel;

    public Settings() {
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

        // Spacer fro the bottom
        HorizontalLayout spacer = new HorizontalLayout();
        spacer.setSizeFull();

        setMargin(false);
        addComponents(pageLayout, spacer);
        setExpandRatio(pageLayout,0.05f);
        setExpandRatio(spacer, 0.95f);
    }
}
