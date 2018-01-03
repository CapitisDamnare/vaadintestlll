package tapsi;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.*;
import tapsi.samples.MainScreen;
import tapsi.samples.authentication.AccessControl;
import tapsi.samples.authentication.BasicAccessControl;
import tapsi.samples.authentication.LoginScreen;
import tapsi.samples.authentication.LoginScreen.LoginListener;

import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import tapsi.samples.data.DataHandler;
import tapsi.samples.logpage.LogPage;
import tapsi.samples.socket.SocketThread;
import tapsi.samples.status.Status;

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
    protected void init(VaadinRequest vaadinRequest) {
        new SocketThread();
        new DataHandler();

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
        getNavigator().navigateTo(LogPage.VIEW_NAME);
    }

    public static MyUI get() {
        return (MyUI) UI.getCurrent();
    }

    public AccessControl getAccessControl() {
        return accessControl;
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
