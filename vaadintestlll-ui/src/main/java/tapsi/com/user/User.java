package tapsi.com.user;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.*;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;
import tapsi.com.data.Client;


public class User extends CssLayout implements View {

    public static final String VIEW_NAME = "User";
    private Label pageLabel;
    private ClientGrid grid;
    private UserForm form;
    private TextField filter;

    private UserLogic viewLogic = new UserLogic(this);
    private Button newProduct;

    private ClientDataProvider dataProvider = new ClientDataProvider();

    public User() {
        setSizeFull();
        addStyleName("crud-view");
        HorizontalLayout pageLayout = createPageLabel();
        HorizontalLayout topLayout = createTopBar();

        grid = new ClientGrid();
        grid.setDataProvider(dataProvider);
        grid.asSingleSelect().addValueChangeListener(
                event -> viewLogic.rowSelected(event.getValue()));
        //grid.setColumns("id","name","phoneID","threadID","allowed","lastConnection");
        //grid.setSizeFull();

        form = new UserForm(viewLogic);
        //form.setCategories(DataService.get().getAllCategories());

        VerticalLayout barAndGridLayout = new VerticalLayout();
        barAndGridLayout.addComponents(pageLayout,topLayout, grid);
        barAndGridLayout.setSizeFull();
        barAndGridLayout.setExpandRatio(grid, 1);
        barAndGridLayout.setStyleName("crud-main-layout");

        addComponent(barAndGridLayout);
        addComponent(form);

        viewLogic.init();
    }

    public HorizontalLayout createPageLabel () {
        HorizontalLayout pageLayout = new HorizontalLayout();
        pageLabel = new Label("Settings");
        pageLabel.setStyleName("page_label");

        pageLayout.setSizeFull();
        pageLayout.setStyleName("page_label_layout");
        pageLayout.setHeight("65px");
        pageLayout.setMargin(false);
        pageLayout.addComponents(pageLabel);
        pageLayout.setComponentAlignment(pageLabel, Alignment.MIDDLE_CENTER);
        return pageLayout;
    }

    public HorizontalLayout createTopBar() {
        filter = new TextField();
        filter.setStyleName("filter-textfield");
        filter.setPlaceholder("Filter name, availability or category");
        ResetButtonForTextField.extend(filter);
        // Apply the filter to grid's data provider. TextField value is never null
        filter.addValueChangeListener(event -> dataProvider.setFilter(event.getValue()));

        newProduct = new Button("New product");
        newProduct.addStyleName(ValoTheme.BUTTON_PRIMARY);
        newProduct.setIcon(VaadinIcons.PLUS_CIRCLE);
        newProduct.addClickListener(click -> viewLogic.newClient());

        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
        topLayout.addComponent(filter);
        topLayout.addComponent(newProduct);
        topLayout.setComponentAlignment(filter, Alignment.MIDDLE_LEFT);
        topLayout.setExpandRatio(filter, 1);
        topLayout.setStyleName("top-bar");
        return topLayout;
    }

    @Override
    public void enter(ViewChangeEvent event) {
        viewLogic.enter(event.getParameters());
    }

    public void showError(String msg) {
        Notification.show(msg, Type.ERROR_MESSAGE);
    }

    public void showSaveNotification(String msg) {
        Notification.show(msg, Type.TRAY_NOTIFICATION);
    }

    public void setNewProductEnabled(boolean enabled) {
        newProduct.setEnabled(enabled);
    }

    public void clearSelection() {
        grid.getSelectionModel().deselectAll();
    }

    public void selectRow(Client row) {
        grid.getSelectionModel().select(row);
    }

    public Client getSelectedRow() {
        return grid.getSelectedRow();
    }

    public void updateProduct(Client client) {
        dataProvider.save(client);
        // FIXME: Grid used to scroll to the updated item
    }

    public void removeClient(Client client) {
        dataProvider.delete(client);
    }

    public void editProduct(Client client) {
        if (client != null) {
            form.addStyleName("visible");
            form.setEnabled(true);
        } else {
            form.removeStyleName("visible");
            form.setEnabled(false);
        }
        form.editProduct(client);
    }
}
