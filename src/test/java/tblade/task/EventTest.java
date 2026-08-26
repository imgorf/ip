package tblade.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests Event's start/end storage and its toString formatting.
 */
public class EventTest {
    @Test
    public void gettersReturnTheStoredStartAndEnd() {
        Event event = new Event("project meeting", "Mon 2pm", "4pm");

        assertEquals("Mon 2pm", event.getFrom());
        assertEquals("4pm", event.getTo());
    }

    @Test
    public void toString_notDone_includesFromAndTo() {
        Event event = new Event("project meeting", "Mon 2pm", "4pm");

        assertEquals("[E][ ] project meeting (from: Mon 2pm to: 4pm)", event.toString());
    }

    @Test
    public void toString_done_showsXIcon() {
        Event event = new Event("project meeting", "Mon 2pm", "4pm");
        event.markAsDone();

        assertEquals("[E][X] project meeting (from: Mon 2pm to: 4pm)", event.toString());
    }
}
