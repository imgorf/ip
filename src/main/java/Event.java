/**
 * Represents a task that starts and ends at specified times.
 */
public class Event extends Task {
    private final String from;
    private final String to;

    /**
     * Creates an unfinished event task.
     *
     * @param description text that describes the event
     * @param from the event start text entered by the user
     * @param to the event end text entered by the user
     */
    public Event(String description, String from, String to) {
        super(description, TaskType.EVENT);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the event start text for storage.
     *
     * @return the event start text
     */
    public String getFrom() {
        return from;
    }

    /**
     * Returns the event end text for storage.
     *
     * @return the event end text
     */
    public String getTo() {
        return to;
    }

    @Override
    protected String getTimeDetails() {
        return " (from: " + from + " to: " + to + ")";
    }
}
