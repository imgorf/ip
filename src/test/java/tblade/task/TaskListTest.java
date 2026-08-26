package tblade.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import tblade.exception.TBladeException;

/**
 * Tests TaskList's add/remove/get/size operations and its 100-task capacity limit.
 */
public class TaskListTest {
    @Test
    public void add_belowCapacity_appendsTask() throws TBladeException {
        TaskList tasks = new TaskList();

        tasks.add(new Todo("read book"));

        assertEquals(1, tasks.size());
        assertEquals("read book", tasks.get(0).getDescription());
    }

    @Test
    public void add_atCapacity_throwsTBladeException() throws TBladeException {
        TaskList tasks = new TaskList();
        for (int i = 0; i < 100; i++) {
            tasks.add(new Todo("task " + i));
        }

        TBladeException exception = assertThrows(TBladeException.class, () -> tasks.add(new Todo("one too many")));

        assertEquals("The task list already has 100 tasks. You cannot add another task in this version.",
                exception.getMessage());
        assertEquals(100, tasks.size());
    }

    @Test
    public void remove_existingIndex_returnsAndDeletesTask() throws TBladeException {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("first"));
        tasks.add(new Todo("second"));

        Task removed = tasks.remove(0);

        assertEquals("first", removed.getDescription());
        assertEquals(1, tasks.size());
        assertEquals("second", tasks.get(0).getDescription());
    }

    @Test
    public void constructor_wrapsExistingList_sharesUnderlyingTasks() {
        List<Task> existing = new ArrayList<>();
        existing.add(new Todo("loaded task"));

        TaskList tasks = new TaskList(existing);

        assertEquals(1, tasks.size());
        assertSame(existing, tasks.getAll());
    }
}
