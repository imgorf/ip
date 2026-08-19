import java.util.Scanner;

/**
 * Runs the TBlade command-line application.
 */
public class TBlade {
    private static final String PIG = " 🐷";
    private static final int MAX_TASKS = 100;

    /**
     * Displays a greeting, stores tasks, changes their completion status, lists them, and exits on {@code bye}.
     *
     * @param args command-line arguments, which are not used by this application
     */
    public static void main(String[] args) {
        String separator = "_".repeat(60);
        String banner = "#####  ####   #       ###   ####   #####\n"
                + "  #    #   #  #      #   #  #   #  #\n"
                + "  #    ####   #      #####  #   #  ####\n"
                + "  #    #   #  #      #   #  #   #  #\n"
                + "  #    ####   #####  #   #  ####   #####";

        printLine(separator);
        banner.lines().forEach(TBlade::printLine);
        printLine("Hello! I'm TBlade.");
        printLine("What can I do for you?");
        printLine(separator);

        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;
        while (true) {
            String command = scanner.nextLine();
            printLine(separator);

            if (command.equals("bye")) {
                printLine("Bye. Hope to see you again soon!");
                printLine(separator);
                break;
            }

            if (command.equals("list")) {
                printLine("Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    printLine((i + 1) + ".[" + tasks[i].getStatusIcon() + "] " + tasks[i].getDescription());
                }
            } else if (command.startsWith("mark ")) {
                int taskNumber = Integer.parseInt(command.substring(5));
                int taskIndex = taskNumber - 1;
                tasks[taskIndex].markAsDone();
                printLine("Nice! I've marked this task as done:");
                printLine("  [X] " + tasks[taskIndex].getDescription());
            } else if (command.startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(command.substring(7));
                int taskIndex = taskNumber - 1;
                tasks[taskIndex].unmarkAsDone();
                printLine("OK, I've marked this task as not done yet:");
                printLine("  [ ] " + tasks[taskIndex].getDescription());
            } else if (taskCount < MAX_TASKS) {
                tasks[taskCount] = new Task(command);
                taskCount++;
                printLine("added: " + command);
            }
            printLine(separator);
        }
    }

    /**
     * Prints one application output line with a pig emoji at its end.
     *
     * @param text text to display before the emoji
     */
    private static void printLine(String text) {
        System.out.println(text + PIG);
    }
}
