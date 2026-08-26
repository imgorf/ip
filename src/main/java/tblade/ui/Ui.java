package tblade.ui;

import java.util.Scanner;

import tblade.task.Task;
import tblade.task.TaskList;

/**
 * Deals with all interactions with the user: reading commands and printing output.
 */
public class Ui {
    private static final String PIG = " 🐷";
    private static final String SEPARATOR = "_".repeat(60);
    private static final String BANNER = "#####  ####   #       ###   ####   #####\n"
            + "  #    #   #  #      #   #  #   #  #\n"
            + "  #    ####   #      #####  #   #  ####\n"
            + "  #    #   #  #      #   #  #   #  #\n"
            + "  #    ####   #####  #   #  ####   #####";

    private final Scanner scanner;

    /**
     * Creates a Ui that reads user commands from standard input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the startup banner and greeting.
     */
    public void showWelcome() {
        showLine();
        BANNER.lines().forEach(this::printLine);
        printLine("Hello! I'm TBlade.");
        printLine("What can I do for you?");
        showLine();
    }

    /**
     * Reads the next full line of user input.
     *
     * @return the command line entered by the user
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Prints a horizontal separator line.
     */
    public void showLine() {
        printLine(SEPARATOR);
    }

    /**
     * Displays an error message to the user.
     *
     * @param message explanation of what went wrong and how to correct it
     */
    public void showError(String message) {
        printLine("OOPS!!! " + message);
    }

    /**
     * Displays the farewell message shown when the user exits.
     */
    public void showGoodbye() {
        printLine("Bye. Hope to see you again soon!");
    }

    /**
     * Displays every task currently in the list, numbered from 1.
     *
     * @param tasks the tasks to display
     */
    public void showTaskList(TaskList tasks) {
        printLine("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            printLine((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Displays the tasks matching a search, numbered from 1.
     *
     * @param matches the matching tasks to display
     */
    public void showMatchingTasks(TaskList matches) {
        printLine("Here are the matching tasks in your list:");
        for (int i = 0; i < matches.size(); i++) {
            printLine((i + 1) + "." + matches.get(i));
        }
    }

    /**
     * Displays the confirmation shown after a task is added.
     *
     * @param task the task that was added
     * @param taskCount number of tasks now in the list
     */
    public void showAddedTask(Task task, int taskCount) {
        printLine("Got it. I've added this task:");
        printLine("  " + task);
        printLine("Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Displays the confirmation shown after a task is marked as done.
     *
     * @param task the task that was marked
     */
    public void showMarked(Task task) {
        printLine("Nice! I've marked this task as done:");
        printLine("  [X] " + task.getDescription());
    }

    /**
     * Displays the confirmation shown after a task is marked as not done.
     *
     * @param task the task that was unmarked
     */
    public void showUnmarked(Task task) {
        printLine("OK, I've marked this task as not done yet:");
        printLine("  [ ] " + task.getDescription());
    }

    /**
     * Displays the confirmation shown after a task is deleted.
     *
     * @param task the task that was removed
     * @param taskCount number of tasks remaining in the list
     */
    public void showDeleted(Task task, int taskCount) {
        printLine("Noted. I've removed this task:");
        printLine("  " + task);
        printLine("Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Prints one application output line with a pig emoji at its end.
     *
     * @param text text to display before the emoji
     */
    private void printLine(String text) {
        System.out.println(text + PIG);
    }
}
