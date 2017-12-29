package tapsi.samples.logpage;

import com.vaadin.navigator.View;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class LogPage extends HorizontalLayout implements View {

    public static final String VIEW_NAME = "Log Page";

    public LogPage() {
        addComponent(new Label("Hier kommt die Log Page"));
    }
}
