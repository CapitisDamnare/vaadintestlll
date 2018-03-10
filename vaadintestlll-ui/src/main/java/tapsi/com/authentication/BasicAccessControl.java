package tapsi.com.authentication;

import java.util.Map;

/**
 * Default mock implementation of {@link AccessControl}. This implementation
 * accepts any string as a password, and considers the user "admin" as the only
 * administrator.
 */
public class BasicAccessControl implements AccessControl {

    private CryptoHandler cryptoHandler = new CryptoHandler();
    private String password = "";

    @Override
    public boolean signIn(String username, String password) {
        if (username == null || username.isEmpty())
            return false;

        if (checkUserCredentials(username, password)) {
            CurrentUser.set(username);
            return true;
        } else
            return false;
    }

    private boolean checkUserCredentials(String username, String password) {
        cryptoHandler.readUsers();
        Map<String, String> map = cryptoHandler.getUserMap();
        return map.containsKey(username) && map.get(username).equals(password);
    }

    @Override
    public boolean isUserSignedIn() {
        return !CurrentUser.get().isEmpty();
    }

    @Override
    public boolean isUserInRole(String role) {
        if ("GDAdmin".equals(role)) {
            // Only the "admin" user is in the "admin" role
            return getPrincipalName().equals("GDAdmin");
        }

        // All users are in all non-admin roles
        return true;
    }

    @Override
    public String getPrincipalName() {
        return CurrentUser.get();
    }

    @Override
    public void safeAdminPassword(String password) {
        cryptoHandler.writeUserToFile("GDAdmin-" + password);
    }
}
