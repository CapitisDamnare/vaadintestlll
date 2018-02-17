package tapsi.com.logpage;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import tapsi.com.data.LogHandler;
import tapsi.com.socket.ObserverHandler;
import tapsi.com.socket.SocketThread;
import tapsi.com.user.ResetButtonForTextField;

public class LogPage extends VerticalLayout implements View, ObserverHandler.SocketListener {

    public static final String VIEW_NAME = "Log Page";
    private Label pageLabel;

    private TextField filter;
    private TextArea log;

    public LogPage() {
        ObserverHandler.addCustomListener(this);
        buildView();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        SocketThread.sendMessage("server:log:GeoDoorVisu");
    }

    @Override
    public void onLogUpdate(String string) {
        log.setValue(string);
    }

    @Override
    public void onClientUpdate() {

    }

    @Override
    public void onAllowed() {

    }

    @Override
    public void onConnected() {

    }

    @Override
    public void onDisconnected() {

    }

    private void buildView() {
        setSizeFull();
        HorizontalLayout pageLayout = new HorizontalLayout();
        pageLabel = new Label("Log Page");
        pageLabel.setStyleName("page_label");

        pageLayout.setSizeFull();
        pageLayout.setStyleName("page_label_layout");
        pageLayout.setHeight("65px");
        pageLayout.setMargin(false);
        pageLayout.addComponents(pageLabel);
        pageLayout.setComponentAlignment(pageLabel, Alignment.MIDDLE_CENTER);

        HorizontalLayout firstRow = new HorizontalLayout();
        firstRow.setSizeFull();

        filter = new TextField();
        //filter.setStyleName("filter-textfield");
        filter.setPlaceholder("Filter");
        filter.setWidth("30%");
        filter.addValueChangeListener(click -> onFilterValueChange());
        //ResetButtonForTextField.extend(filter);
        //TODO: Add a Filter
        //filter.addValueChangeListener(event -> dataProvider.setFilter(event.getValue()));

        firstRow.addComponent(filter);

        HorizontalLayout secondRow = new HorizontalLayout();
        secondRow.setSizeFull();

        log = new TextArea("Logs");
        log.setValue("This are the servers last Messsages");
        log.setStyleName("status_log");
        log.setSizeFull();
        log.setEnabled(false);

        secondRow.addComponent(log);

        // Spacer fro the bottom
        HorizontalLayout spacer = new HorizontalLayout();
        spacer.setSizeFull();

        setMargin(false);
        addComponents(pageLayout, firstRow, secondRow, spacer);
        setExpandRatio(pageLayout,0.08f);
        setExpandRatio(firstRow, 0.05f);
        setExpandRatio(secondRow, 0.87f);
        setExpandRatio(spacer, 0.1f);
    }

    private void onFilterValueChange() {
        log.setValue(LogHandler.filterLogs(filter.getValue()));
    }
}
