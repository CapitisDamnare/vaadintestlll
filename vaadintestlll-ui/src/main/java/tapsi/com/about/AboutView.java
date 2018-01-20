package tapsi.com.about;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.Version;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;

public class AboutView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "About";
    private Label pageLabel;

    public AboutView() {
        setSizeFull();
        HorizontalLayout pageLayout = new HorizontalLayout();
        pageLabel = new Label("About");
        pageLabel.setStyleName("page_label");

        pageLayout.setSizeFull();
        pageLayout.setStyleName("page_label_layout");
        pageLayout.setHeight("65px");
        pageLayout.setMargin(false);
        pageLayout.addComponents(pageLabel);
        pageLayout.setComponentAlignment(pageLabel, Alignment.MIDDLE_CENTER);


        CustomLayout aboutContent = new CustomLayout("aboutview");
        aboutContent.setStyleName("about-content");

        // you can add Vaadin components in predefined slots in the custom
        // layout
        aboutContent.addComponent(
                new Label(VaadinIcons.INFO_CIRCLE.getHtml()
                        + " This application is using Vaadin "
                        + Version.getFullVersion(), ContentMode.HTML), "info");

        setMargin(false);
        setStyleName("about-view");
        addComponents(pageLayout,aboutContent);
        setComponentAlignment(aboutContent, Alignment.MIDDLE_CENTER);
        setExpandRatio(pageLayout,0.05f);
        setExpandRatio(aboutContent, 0.95f);
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }

}
