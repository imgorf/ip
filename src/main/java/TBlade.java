import java.util.ArrayList;
import java.util.List;
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
        List<Task> tasks = new ArrayList<>();
        while (true) {
            String command = scanner.nextLine();
            printLine(separator);

            try {
                if (command.isBlank()) {
                    throw new TBladeException("Please enter a command. Use: todo, deadline, event, list, mark, unmark, delete, or bye.");
                } else if (command.equals("bye")) {
                    printLine("Bye. Hope to see you again soon!");
                    printLine(separator);
                    break;
                } else if (command.startsWith("bye ")) {
                    throw new TBladeException("The bye command does not take extra text. Use: bye");
                } else if (command.equals("list")) {
                    printLine("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        printLine((i + 1) + "." + tasks.get(i));
                    }
                } else if (command.startsWith("list ")) {
                    throw new TBladeException("The list command does not take extra text. Use: list");
                } else if (command.equals("mark") || command.startsWith("mark ")) {
                    int taskIndex = getTaskIndex(command, "mark", tasks.size());
                    tasks.get(taskIndex).markAsDone();
                    printLine("Nice! I've marked this task as done:");
                    printLine("  [X] " + tasks.get(taskIndex).getDescription());
                } else if (command.equals("unmark") || command.startsWith("unmark ")) {
                    int taskIndex = getTaskIndex(command, "unmark", tasks.size());
                    tasks.get(taskIndex).unmarkAsDone();
                    printLine("OK, I've marked this task as not done yet:");
                    printLine("  [ ] " + tasks.get(taskIndex).getDescription());
                } else if (command.equals("delete") || command.startsWith("delete ")) {
                    int taskIndex = getTaskIndex(command, "delete", tasks.size());
                    Task removedTask = tasks.remove(taskIndex);
                    printLine("Noted. I've removed this task:");
                    printLine("  " + removedTask);
                    printLine("Now you have " + tasks.size() + " tasks in the list.");
                } else if (command.equals("todo") || command.startsWith("todo ")) {
                    String description = command.substring(4).trim();
                    if (description.isEmpty()) {
                        throw new TBladeException("The description of a todo cannot be empty. Use: todo DESCRIPTION");
                    }
                    checkTaskListHasSpace(tasks.size());
                    tasks.add(new Todo(description));
                    printAddedTask(tasks.get(tasks.size() - 1), tasks.size());
                } else if (command.equals("deadline") || command.startsWith("deadline ")) {
                    String details = command.substring(8).trim();
                    if (details.isEmpty() || details.startsWith("/by")) {
                        throw new TBladeException("The description of a deadline cannot be empty. "
                                + "Use: deadline DESCRIPTION /by DATE");
                    }
                    int byIndex = details.indexOf(" /by");
                    if (byIndex < 0) {
                        throw new TBladeException("A deadline needs a /by date. "
                                + "Use: deadline DESCRIPTION /by DATE");
                    }
                    String description = details.substring(0, byIndex).trim();
                    String by = details.substring(byIndex + 4).trim();
                    if (by.isEmpty()) {
                        throw new TBladeException("The /by date cannot be empty. "
                                + "Use: deadline DESCRIPTION /by DATE");
                    }
                    checkTaskListHasSpace(tasks.size());
                    tasks.add(new Deadline(description, by));
                    printAddedTask(tasks.get(tasks.size() - 1), tasks.size());
                } else if (command.equals("event") || command.startsWith("event ")) {
                    String details = command.substring(5).trim();
                    if (details.isEmpty() || details.startsWith("/from")) {
                        throw new TBladeException("The description of an event cannot be empty. "
                                + "Use: event DESCRIPTION /from START /to END");
                    }
                    int fromIndex = details.indexOf(" /from");
                    if (fromIndex < 0) {
                        throw new TBladeException("An event needs a /from start time. "
                                + "Use: event DESCRIPTION /from START /to END");
                    }
                    String description = details.substring(0, fromIndex).trim();
                    String times = details.substring(fromIndex + 6).trim();
                    if (times.isEmpty()) {
                        throw new TBladeException("The /from start time cannot be empty. "
                                + "Use: event DESCRIPTION /from START /to END");
                    }
                    int toIndex = times.indexOf(" /to");
                    if (toIndex < 0) {
                        throw new TBladeException("An event needs a /to end time. "
                                + "Use: event DESCRIPTION /from START /to END");
                    }
                    String from = times.substring(0, toIndex).trim();
                    String to = times.substring(toIndex + 4).trim();
                    if (from.isEmpty()) {
                        throw new TBladeException("The /from start time cannot be empty. "
                                + "Use: event DESCRIPTION /from START /to END");
                    }
                    if (to.isEmpty()) {
                        throw new TBladeException("The /to end time cannot be empty. "
                                + "Use: event DESCRIPTION /from START /to END");
                    }
                    checkTaskListHasSpace(tasks.size());
                    tasks.add(new Event(description, from, to));
                    printAddedTask(tasks.get(tasks.size() - 1), tasks.size());
                } else {
                    throw new TBladeException("I don't know that command. Use: todo, deadline, event, list, mark, unmark, delete, or bye.");
                }
            } catch (TBladeException exception) {
                printLine("OOPS!!! " + exception.getMessage());
            }
            printLine(separator);
        }
    }

    /**
     * Converts a command's task number to a valid zero-based list index.
     *
     * @param command full command entered by the user
     * @param commandWord the command word before its number
     * @param taskCount number of tasks currently stored
     * @return the zero-based index of the requested task
     * @throws TBladeException if the command does not contain a valid task number
     */
    private static int getTaskIndex(String command, String commandWord, int taskCount) throws TBladeException {
        String taskNumberText = command.substring(commandWord.length()).trim();
        if (taskNumberText.isEmpty()) {
            throw new TBladeException("Please provide a task number. Use: " + commandWord + " TASK_NUMBER");
        }
        if (taskCount == 0) {
            throw new TBladeException("There are no tasks to " + commandWord + ". Add a task first.");
        }
        try {
            int taskNumber = Integer.parseInt(taskNumberText);
            if (taskNumber < 1 || taskNumber > taskCount) {
                throw new TBladeException("Task number must be between 1 and " + taskCount + ". "
                        + "Use: " + commandWord + " TASK_NUMBER");
            }
            return taskNumber - 1;
        } catch (NumberFormatException exception) {
            throw new TBladeException("Task number must be a whole number. "
                    + "Use: " + commandWord + " TASK_NUMBER");
        }
    }

    /**
     * Ensures that another task can be stored in the task list.
     *
     * @param taskCount number of tasks currently stored
     * @throws TBladeException if the list has reached its capacity
     */
    private static void checkTaskListHasSpace(int taskCount) throws TBladeException {
        if (taskCount >= MAX_TASKS) {
            throw new TBladeException("The task list already has " + MAX_TASKS + " tasks. "
                    + "You cannot add another task in this version.");
        }
    }

    /**
     * Displays the confirmation shown after a task is added.
     *
     * @param task the task that was added
     * @param taskCount number of tasks now in the list
     */
    private static void printAddedTask(Task task, int taskCount) {
        printLine("Got it. I've added this task:");
        printLine("  " + task);
        printLine("Now you have " + taskCount + " tasks in the list.");
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
