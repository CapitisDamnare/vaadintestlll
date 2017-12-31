package tapsi.samples.backend.data;

public enum Availability {
    ALLOWED(1), NOTALLOWED(0);

    private final int allowed;

    private Availability(int allowed) {
        this.allowed = allowed;
    }

    @Override
    public String toString() {
        return Integer.toString(allowed);
    }
}
