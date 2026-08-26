package tblade.task;

/**
 * Identifies the supported kinds of tasks and their list-display icons.
 */
public enum TaskType {
    TODO("T"),
    DEADLINE("D"),
    EVENT("E");

    private final String icon;

    /**
     * Creates a task type with its one-letter display icon.
     *
     * @param icon icon shown before a task's completion status
     */
    TaskType(String icon) {
        this.icon = icon;
    }

    /**
     * Returns the icon used to display this task type.
     *
     * @return the one-letter task type icon
     */
    public String getIcon() {
        return icon;
    }
}
