package tblade.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tblade.exception.TBladeException;
import tblade.task.TaskList;
import tblade.task.Todo;

/**
 * Tests Ui's printed output by capturing System.out, since Ui's job is entirely
 * about producing exactly the right console text.
 */
public class UiTest {
    private final ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();
    private PrintStream originalOut;
    private Ui ui;

    @BeforeEach
    public void redirectSystemOut() {
        originalOut = System.out;
        System.setOut(new PrintStream(capturedOutput));
        ui = new Ui();
    }

    @AfterEach
    public void restoreSystemOut() {
        System.setOut(originalOut);
    }

    @Test
    public void showError_printsMessageWithOopsPrefix() {
        ui.showError("The description of a todo cannot be empty. Use: todo DESCRIPTION");

        assertEquals("OOPS!!! The description of a todo cannot be empty. Use: todo DESCRIPTION 🐷\n",
                capturedOutput.toString());
    }

    @Test
    public void showAddedTask_printsConfirmationAndTaskCount() {
        ui.showAddedTask(new Todo("read book"), 1);

        assertEquals(
                "Got it. I've added this task: 🐷\n"
                        + "  [T][ ] read book 🐷\n"
                        + "Now you have 1 tasks in the list. 🐷\n",
                capturedOutput.toString());
    }

    @Test
    public void showTaskList_printsEachTaskNumberedFromOne() throws TBladeException {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("first task"));
        tasks.add(new Todo("second task"));

        ui.showTaskList(tasks);

        assertEquals(
                "Here are the tasks in your list: 🐷\n"
                        + "1.[T][ ] first task 🐷\n"
                        + "2.[T][ ] second task 🐷\n",
                capturedOutput.toString());
    }

    @Test
    public void showMatchingTasks_printsEachMatchNumberedFromOne() throws TBladeException {
        TaskList matches = new TaskList();
        matches.add(new Todo("read book"));

        ui.showMatchingTasks(matches);

        assertEquals(
                "Here are the matching tasks in your list: 🐷\n"
                        + "1.[T][ ] read book 🐷\n",
                capturedOutput.toString());
    }
}
