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
    public void formatError_returnsMessageWithTechnobladePrefix() {
        assertEquals("Chat, is this real? The description of a todo cannot be empty. Use: todo DESCRIPTION",
                ui.formatError("The description of a todo cannot be empty. Use: todo DESCRIPTION"));
    }

    @Test
    public void formatGoodbye_returnsFarewellMessage() {
        assertEquals("gg. Technoblade never dies — I'll be right here when you're back.", ui.formatGoodbye());
    }

    @Test
    public void formatAddedTask_returnsConfirmationAndTaskCount() {
        assertEquals(
                "Added to the kill list:\n"
                        + "  [T][ ] read book\n"
                        + "Now you have 1 tasks on the kill list.",
                ui.formatAddedTask(new Todo("read book"), 1));
    }

    @Test
    public void formatMarked_returnsConfirmationWithXIcon() {
        assertEquals("GG! Task conquered:\n  [X] read book",
                ui.formatMarked(new Todo("read book")));
    }

    @Test
    public void formatUnmarked_returnsConfirmationWithEmptyIcon() {
        assertEquals("Back in the fight — not done yet:\n  [ ] read book",
                ui.formatUnmarked(new Todo("read book")));
    }

    @Test
    public void formatDeleted_returnsConfirmationAndTaskCount() {
        assertEquals(
                "Wiped from the kill list:\n"
                        + "  [T][ ] read book\n"
                        + "Now you have 0 tasks on the kill list.",
                ui.formatDeleted(new Todo("read book"), 0));
    }

    @Test
    public void formatTaskList_listsEachTaskNumberedFromOne() {
        TaskList tasks = TaskList.of(new Todo("first task"), new Todo("second task"));

        assertEquals(
                "Here's your kill list:\n"
                        + "1.[T][ ] first task\n"
                        + "2.[T][ ] second task",
                ui.formatTaskList(tasks));
    }

    @Test
    public void formatMatchingTasks_listsEachMatchNumberedFromOne() {
        TaskList matches = TaskList.of(new Todo("read book"));

        assertEquals("Matches found on the kill list:\n1.[T][ ] read book",
                ui.formatMatchingTasks(matches));
    }

    @Test
    public void formatTaskList_emptyList_showsOnlyTheHeader() {
        assertEquals("Here's your kill list:", ui.formatTaskList(new TaskList()));
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

        String lineSeparator = System.lineSeparator();
        assertEquals("Got it. I've added this task: 🐷" + lineSeparator + "  [T][ ] read book 🐷" + lineSeparator,
                capturedOutput.toString());
    }
}
