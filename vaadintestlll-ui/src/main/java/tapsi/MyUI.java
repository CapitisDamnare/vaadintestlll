package tapsi;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.*;
import com.vaadin.navigator.ViewBeforeLeaveEvent;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewLeaveAction;
import tapsi.com.MainScreen;
import tapsi.com.authentication.AccessControl;
import tapsi.com.authentication.BasicAccessControl;
import tapsi.com.authentication.LoginScreen;
import tapsi.com.authentication.LoginScreen.LoginListener;

import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import tapsi.com.data.DataHandler;
import tapsi.com.data.LogHandler;
import tapsi.com.socket.SocketThread;
import tapsi.com.status.Status;

/**
 * Main UI class of the application that shows either the login screen or the
 * main view of the application depending on whether a user is signed in.
 *
 * The @Viewport annotation configures the viewport meta tags appropriately on
 * mobile devices. Instead of device based scaling (default), using responsive
 * layouts.
 */
@Viewport("user-scalable=no,initial-scale=1.0")
@Theme("mytheme")
@Widgetset("tapsi.MyAppWidgetset")
@PreserveOnRefresh
public class MyUI extends UI {

    private AccessControl accessControl = new BasicAccessControl();

    @Override
    protected void refresh(VaadinRequest request) {
        getNavigator().getCurrentView().enter(null);
        super.refresh(request);
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        new SocketThread();
        new DataHandler();
        new LogHandler();
        setPollInterval(2000);

        Responsive.makeResponsive(this);
        setLocale(vaadinRequest.getLocale());
        getPage().setTitle("Geo Door Server");
        if (!accessControl.isUserSignedIn()) {
            setContent(new LoginScreen(accessControl, new LoginListener() {
                @Override
                public void loginSuccessful() {
                    showMainView();
                }
            }));
        } else {
            showMainView();
        }
    }

    protected void showMainView() {
        addStyleName(ValoTheme.UI_WITH_MENU);
        setContent(new MainScreen(MyUI.this));
        getNavigator().navigateTo(Status.VIEW_NAME);
    }

    public static MyUI get() {
        return (MyUI) UI.getCurrent();
    }

    public AccessControl getAccessControl() {
        return accessControl;
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = true)
    public static class MyUIServlet extends VaadinServlet {
    }
}
