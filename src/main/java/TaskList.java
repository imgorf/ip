import java.util.ArrayList;
import java.util.List;

/**
 * Holds the tasks currently known to the application and the operations that add to,
 * remove from, and enforce the capacity of that list.
 */
public class TaskList {
    private static final int MAX_TASKS = 100;

    private final List<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this(new ArrayList<>());
    }

    /**
     * Wraps an existing list of tasks, such as one just loaded from storage.
     *
     * @param tasks tasks to wrap
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the end of the list.
     *
     * @param task task to add
     * @throws TBladeException if the list has reached its capacity
     */
    public void add(Task task) throws TBladeException {
        if (tasks.size() >= MAX_TASKS) {
            throw new TBladeException("The task list already has " + MAX_TASKS + " tasks. "
                    + "You cannot add another task in this version.");
        }
        tasks.add(task);
    }

    /**
     * Removes and returns the task at the given index.
     *
     * @param index zero-based index of the task to remove
     * @return the removed task
     */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns the task at the given index.
     *
     * @param index zero-based index of the task
     * @return the task at that index
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks currently in the list.
     *
     * @return the task count
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the underlying tasks, e.g. for saving to storage.
     *
     * @return the tasks in this list
     */
    public List<Task> getAll() {
        return tasks;
    }
}
