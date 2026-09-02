package tblade.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tblade.task.TaskList;
import tblade.task.Todo;

/**
 * Tests Ui's response text: the {@code formatX} methods are pure functions checked directly,
 * and {@code showMessage} (the one method that does I/O) is checked by capturing System.out.
 */
public class UiTest {
    private Ui ui;

    @BeforeEach
    public void createUi() {
        ui = new Ui();
    }

    @Test
    public void formatError_returnsMessageWithOopsPrefix() {
        assertEquals("OOPS!!! The description of a todo cannot be empty. Use: todo DESCRIPTION",
                ui.formatError("The description of a todo cannot be empty. Use: todo DESCRIPTION"));
    }

    @Test
    public void formatGoodbye_returnsFarewellMessage() {
        assertEquals("Bye. Hope to see you again soon!", ui.formatGoodbye());
    }

    @Test
    public void formatAddedTask_returnsConfirmationAndTaskCount() {
        assertEquals(
                "Got it. I've added this task:\n"
                        + "  [T][ ] read book\n"
                        + "Now you have 1 tasks in the list.",
                ui.formatAddedTask(new Todo("read book"), 1));
    }

    @Test
    public void formatMarked_returnsConfirmationWithXIcon() {
        assertEquals("Nice! I've marked this task as done:\n  [X] read book",
                ui.formatMarked(new Todo("read book")));
    }

    @Test
    public void formatUnmarked_returnsConfirmationWithEmptyIcon() {
        assertEquals("OK, I've marked this task as not done yet:\n  [ ] read book",
                ui.formatUnmarked(new Todo("read book")));
    }

    @Test
    public void formatDeleted_returnsConfirmationAndTaskCount() {
        assertEquals(
                "Noted. I've removed this task:\n"
                        + "  [T][ ] read book\n"
                        + "Now you have 0 tasks in the list.",
                ui.formatDeleted(new Todo("read book"), 0));
    }

    @Test
    public void formatTaskList_listsEachTaskNumberedFromOne() {
        TaskList tasks = TaskList.of(new Todo("first task"), new Todo("second task"));

        assertEquals(
                "Here are the tasks in your list:\n"
                        + "1.[T][ ] first task\n"
                        + "2.[T][ ] second task",
                ui.formatTaskList(tasks));
    }

    @Test
    public void formatMatchingTasks_listsEachMatchNumberedFromOne() {
        TaskList matches = TaskList.of(new Todo("read book"));

        assertEquals("Here are the matching tasks in your list:\n1.[T][ ] read book",
                ui.formatMatchingTasks(matches));
    }

    @Test
    public void formatTaskList_emptyList_showsOnlyTheHeader() {
        assertEquals("Here are the tasks in your list:", ui.formatTaskList(new TaskList()));
    }

    @Test
    public void showMessage_printsEachLineWithPigEmoji() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOutput));
        try {
            ui.showMessage("Got it. I've added this task:\n  [T][ ] read book");
        } finally {
            System.setOut(originalOut);
        }

        assertEquals("Got it. I've added this task: 🐷\n  [T][ ] read book 🐷\n", capturedOutput.toString());
    }
}
