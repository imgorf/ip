package tblade.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Tests Deadline's date storage and its non-trivial display formatting
 * (LocalDate reformatted from yyyy-MM-dd storage form to "MMM dd uuuu" display form).
 */
public class DeadlineTest {
    @Test
    public void getBy_returnsTheStoredDate() {
        Deadline deadline = new Deadline("return book", LocalDate.of(2026, 3, 15));

        assertEquals(LocalDate.of(2026, 3, 15), deadline.getBy());
    }

    @Test
    public void toString_formatsDateAsMmmDdUuuu() {
        Deadline deadline = new Deadline("return book", LocalDate.of(2026, 3, 15));

        assertEquals("[D][ ] return book (by: Mar 15 2026)", deadline.toString());
    }

    @Test
    public void toString_singleDigitDay_isZeroPadded() {
        Deadline deadline = new Deadline("return book", LocalDate.of(2026, 6, 6));

        assertEquals("[D][ ] return book (by: Jun 06 2026)", deadline.toString());
    }

    @Test
    public void toString_done_showsXIcon() {
        Deadline deadline = new Deadline("return book", LocalDate.of(2026, 3, 15));
        deadline.markAsDone();

        assertEquals("[D][X] return book (by: Mar 15 2026)", deadline.toString());
    }
}
