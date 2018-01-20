package tapsi.com.user;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.renderers.NumberRenderer;
import tapsi.com.data.Client;

public class ClientGrid extends Grid<Client> {

    public ClientGrid() {
        setSizeFull();

        addColumn(Client::getId, new NumberRenderer()).setCaption("Id");
        addColumn(Client::getName).setCaption("Name");
        addColumn(Client::getPhoneID).setCaption("Phone ID");
        addColumn(Client::getThreadID).setCaption("Thread ID");

        // Add an traffic light icon in front of availability
        addColumn(this::htmlFormatAllowance, new HtmlRenderer())
                .setCaption("Allowance").setComparator((p1, p2) -> {
            return p1.getAllowed().toString()
                    .compareTo(p2.getAllowed().toString());
        });
        addColumn(Client::getLastConnection).setCaption("Last Connection");
    }

    public Client getSelectedRow() {
        return asSingleSelect().getValue();
    }

    public void refresh(Client client) {
        getDataCommunicator().refresh(client);
    }

    private String htmlFormatAllowance(Client client) {
        Allowance allowance = client.getAllowed();
        String text = allowance.toString();

        String color = "";
        switch (allowance) {
            case ALLOWED:
                color = "#2dd085";
                break;
            case NOTALLOWED:
                color = "#ffc66e";
                break;
            default:
                break;
        }

        String iconCode = "<span class=\"v-icon\" style=\"font-family: "
                + VaadinIcons.CIRCLE.getFontFamily() + ";color:" + color
                + "\">&#x"
                + Integer.toHexString(VaadinIcons.CIRCLE.getCodepoint())
                + ";</span>";

        return iconCode + " " + text;
    }
}
