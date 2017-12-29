package tapsi.samples.settings;

import com.vaadin.navigator.View;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class Settings extends HorizontalLayout implements View {

    public static final String VIEW_NAME = "Settings";

    public Settings() {
        addComponent(new Label("Hier kommen die Settings"));
    }
}
