import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Represents a task that must be completed by a specified time.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM dd uuuu", Locale.ENGLISH);
    private final LocalDate by;

    /**
     * Creates an unfinished deadline task.
     *
     * @param description text that describes the task
     * @param by the date by which the task must be completed
     */
    public Deadline(String description, LocalDate by) {
        super(description, TaskType.DEADLINE);
        this.by = by;
    }

    /**
     * Returns the deadline date for storage.
     *
     * @return the deadline date
     */
    public LocalDate getBy() {
        return by;
    }

    @Override
    protected String getTimeDetails() {
        return " (by: " + by.format(DISPLAY_FORMAT) + ")";
    }
}
