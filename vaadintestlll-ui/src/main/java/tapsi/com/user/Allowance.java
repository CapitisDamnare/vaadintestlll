package tapsi.com.user;

public enum Allowance {
    ALLOWED(1), NOTALLOWED(0);

    private final int allowed;

    private Allowance(int allowed) {
        this.allowed = allowed;
    }

    @Override
    public String toString() {
        return Integer.toString(allowed);
    }
}
