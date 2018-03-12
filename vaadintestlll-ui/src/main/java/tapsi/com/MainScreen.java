package tapsi.com;

import tapsi.MyUI;
import tapsi.com.about.AboutView;
import tapsi.com.user.User;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import tapsi.com.logpage.LogPage;
import tapsi.com.settings.Settings;
import tapsi.com.status.Status;

/**
 * Creates the menu, all views and ads them to the navigator.
 * The view in the CSSLayout will be changed from the navigator.
 * 
 */
public class MainScreen extends HorizontalLayout {
    private Menu menu;

    public MainScreen(MyUI ui) {

        setSpacing(false);
        setStyleName("main-screen");
        setWidth("100%");
        setHeight("100%");

        CssLayout viewContainer = new CssLayout();
        viewContainer.addStyleName("valo-content");
        viewContainer.setWidth("100%");

        final Navigator navigator = new Navigator(ui, viewContainer);
        navigator.setErrorView(ErrorView.class);
        menu = new Menu(navigator);
        menu.addView(new Status(), Status.VIEW_NAME,
                Status.VIEW_NAME, VaadinIcons.HOME);
        menu.addView(new User(), User.VIEW_NAME,
                User.VIEW_NAME, VaadinIcons.USERS);
        menu.addView(new LogPage(), LogPage.VIEW_NAME,
                LogPage.VIEW_NAME, VaadinIcons.SEARCH);
        menu.addView(new Settings(), Settings.VIEW_NAME,
                Settings.VIEW_NAME, VaadinIcons.COG);
        menu.addView(new AboutView(), AboutView.VIEW_NAME,
                AboutView.VIEW_NAME, VaadinIcons.INFO_CIRCLE);

        navigator.addViewChangeListener(viewChangeListener);

        addComponent(menu);
        addComponent(viewContainer);
        setExpandRatio(viewContainer, 1);
        //setSizeFull();
    }

    // notify the view menu about view changes so that it can display which view
    // is currently active
    ViewChangeListener viewChangeListener = new ViewChangeListener() {

        @Override
        public boolean beforeViewChange(ViewChangeEvent event) {
            return true;
        }

        @Override
        public void afterViewChange(ViewChangeEvent event) {
            menu.setActiveView(event.getViewName());
        }

    };
}
