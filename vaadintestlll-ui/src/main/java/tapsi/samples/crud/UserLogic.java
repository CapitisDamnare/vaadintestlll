package tapsi.samples.crud;

import com.vaadin.server.Page;
import tapsi.MyUI;
import tapsi.samples.socket.SocketConnector;

import java.io.Serializable;

public class UserLogic implements Serializable {
    private User view;

    public UserLogic(User view) {
        this.view = view;
    }

    public void init() {
        editProduct(null);
        // Hide and disable if not admin
        if (!MyUI.get().getAccessControl().isUserInRole("admin")) {
            view.setNewProductEnabled(false);
        }
    }

    public void cancelProduct() {
        setFragmentParameter("");
        view.clearSelection();
    }

    /**
     * Update the fragment without causing navigator to change view
     */
    private void setFragmentParameter(String clientID) {
        String fragmentParameter;
        if (clientID == null || clientID.isEmpty()) {
            fragmentParameter = "";
        } else {
            fragmentParameter = clientID;
        }

        Page page = MyUI.get().getPage();
        page.setUriFragment(
                "!" + User.VIEW_NAME + "/" + fragmentParameter, false);
    }

    public void enter(String clientID) {
        if (clientID != null && !clientID.isEmpty()) {
            if (clientID.equals("new")) {
                newClient();
            } else {
                // Ensure this is selected even if coming directly here from
                // login
                try {
                    int pid = Integer.parseInt(clientID);
                    Client client = findClient(pid);
                    view.selectRow(client);
                } catch (NumberFormatException e) {
                }
            }
        }
    }

    private Client findClient(int clientID) {
        //return DataService.get().getProductById(productId);
        return SocketConnector.getProductByID(clientID);
    }

    public void saveProduct(Client client) {
        view.showSaveNotification(client.getName() + " ("
                + client.getId() + ") updated");
        view.clearSelection();
        view.updateProduct(client);
        setFragmentParameter("");
    }

    public void deleteProduct(Client client) {
        view.showSaveNotification(client.getName() + " ("
                + client.getId() + ") removed");
        view.clearSelection();
        view.removeClient(client);
        setFragmentParameter("");
    }

    public void editProduct(Client client) {
        if (client == null) {
            setFragmentParameter("");
        } else {
            setFragmentParameter(client.getId() + "");
        }
        view.editProduct(client);
    }

    public void newClient() {
        view.clearSelection();
        setFragmentParameter("new");
        view.editProduct(new Client());
    }

    public void rowSelected(Client client) {
        if (MyUI.get().getAccessControl().isUserInRole("admin")) {
            view.editProduct(client);
        }
    }
}
