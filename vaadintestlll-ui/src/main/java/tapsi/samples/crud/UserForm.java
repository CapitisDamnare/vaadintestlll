package tapsi.samples.crud;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.Binder;
import com.vaadin.server.Page;
import tapsi.samples.socket.SocketConnector;

public class UserForm extends UserFormDesign {

    private UserLogic viewLogic;
    private Client currentUser;
    private Binder<Client> binder;

    public UserForm(UserLogic viewLogic) {
        this.viewLogic = viewLogic;
        addStyleName("product-form");

        allowed.setItems(Allowance.values());
        allowed.setEmptySelectionAllowed(false);

        binder = new BeanValidationBinder<>(Client.class);
        binder.forField(name).bind("name");
        binder.forField(phoneID).bind("phoneID");

        binder.bindInstanceFields(this);

        // enable/disable save button while editing
        binder.addStatusChangeListener(event -> {
            boolean isValid = !event.hasValidationErrors();
            boolean hasChanges = binder.hasChanges();
            save.setEnabled(hasChanges && isValid);
            discard.setEnabled(hasChanges);
        });


        save.addClickListener(event -> {
            if (currentUser != null
                    && binder.writeBeanIfValid(currentUser)) {
                viewLogic.saveProduct(currentUser);
            }
        });

        discard.addClickListener(
                event -> viewLogic.editProduct(currentUser));

        cancel.addClickListener(event -> viewLogic.cancelProduct());

        delete.addClickListener(event -> {
            if (currentUser != null) {
                viewLogic.deleteProduct(currentUser);
            }
        });

    }

    public void editProduct(Client client) {
        if (client == null) {
            client = new Client();
            SocketConnector.addUser(client);
        }
        currentUser = client;
        binder.readBean(client);

        // Scroll to the top
        // As this is not a Panel, using JavaScript
        String scrollScript = "window.document.getElementById('" + getId()
                + "').scrollTop = 0;";
        Page.getCurrent().getJavaScript().execute(scrollScript);
    }
}
