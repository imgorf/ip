package tblade.task;

/**
 * Represents a task with no date or time attached to it.
 */
public class Todo extends Task {
    /**
     * Creates an unfinished todo task.
     *
     * @param description text that describes the todo
     */
    public Todo(String description) {
        super(description, TaskType.TODO);
    }

    @Override
    protected String getTimeDetails() {
        return "";
    }
}
