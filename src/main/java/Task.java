/**
 * Represents one task in the task list and whether it has been completed.
 */
public abstract class Task {
    private final String description;
    private boolean isDone;

    /**
     * Creates an unfinished task with the given description.
     *
     * @param description text that describes the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks this task as completed.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks this task as not completed.
     */
    public void unmarkAsDone() {
        isDone = false;
    }

    /**
     * Returns the completion icon used when displaying this task.
     *
     * @return {@code X} for a completed task, or a space otherwise
     */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Returns the text that describes this task.
     *
     * @return the task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the icon that identifies this task's type.
     *
     * @return a one-letter task type icon
     */
    protected abstract String getTypeIcon();

    /**
     * Returns any date or time details belonging to this task.
     *
     * @return formatted date or time details, or an empty string for a todo
     */
    protected abstract String getTimeDetails();

    /**
     * Returns this task in the format used by the task list.
     *
     * @return formatted task text
     */
    @Override
    public String toString() {
        return "[" + getTypeIcon() + "][" + getStatusIcon() + "] " + description + getTimeDetails();
    }
}
