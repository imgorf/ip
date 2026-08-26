import java.util.ArrayList;
import java.util.List;

/**
 * Runs the TBlade command-line application, coordinating the Ui, Storage, and TaskList.
 */
public class TBlade {
    private final Storage storage;
    private final Ui ui;
    private TaskList tasks;

    /**
     * Creates a TBlade that persists its tasks to the given file path, loading any tasks
     * already saved there.
     *
     * @param filePath path of the file used to store tasks
     */
    public TBlade(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        List<Task> loadedTasks;
        try {
            loadedTasks = storage.load();
        } catch (TBladeException exception) {
            ui.showError(exception.getMessage());
            loadedTasks = new ArrayList<>();
        }
        tasks = new TaskList(loadedTasks);
    }

    /**
     * Greets the user, then repeatedly reads and executes commands until {@code bye} is entered.
     */
    public void run() {
        ui.showWelcome();
        boolean isRunning = true;
        while (isRunning) {
            String command = ui.readCommand();
            ui.showLine();
            try {
                isRunning = executeCommand(command);
            } catch (TBladeException exception) {
                ui.showError(exception.getMessage());
            }
            ui.showLine();
        }
    }

    /**
     * Interprets and carries out a single command.
     *
     * @param command full command entered by the user
     * @return {@code false} if the command was {@code bye}, {@code true} otherwise
     * @throws TBladeException if the command is invalid
     */
    private boolean executeCommand(String command) throws TBladeException {
        if (command.isBlank()) {
            throw new TBladeException("Please enter a command. Use: todo, deadline, event, list, mark, unmark, delete, or bye.");
        } else if (command.equals("bye")) {
            ui.showGoodbye();
            return false;
        } else if (command.startsWith("bye ")) {
            throw new TBladeException("The bye command does not take extra text. Use: bye");
        } else if (command.equals("list")) {
            ui.showTaskList(tasks);
        } else if (command.startsWith("list ")) {
            throw new TBladeException("The list command does not take extra text. Use: list");
        } else if (command.equals("mark") || command.startsWith("mark ")) {
            int taskIndex = Parser.parseTaskIndex(command, "mark", tasks.size());
            tasks.get(taskIndex).markAsDone();
            storage.save(tasks.getAll());
            ui.showMarked(tasks.get(taskIndex));
        } else if (command.equals("unmark") || command.startsWith("unmark ")) {
            int taskIndex = Parser.parseTaskIndex(command, "unmark", tasks.size());
            tasks.get(taskIndex).unmarkAsDone();
            storage.save(tasks.getAll());
            ui.showUnmarked(tasks.get(taskIndex));
        } else if (command.equals("delete") || command.startsWith("delete ")) {
            int taskIndex = Parser.parseTaskIndex(command, "delete", tasks.size());
            Task removedTask = tasks.remove(taskIndex);
            storage.save(tasks.getAll());
            ui.showDeleted(removedTask, tasks.size());
        } else if (command.equals("todo") || command.startsWith("todo ")) {
            String description = Parser.getTodoDescription(command);
            tasks.add(new Todo(description));
            storage.save(tasks.getAll());
            ui.showAddedTask(tasks.get(tasks.size() - 1), tasks.size());
        } else if (command.equals("deadline") || command.startsWith("deadline ")) {
            Parser.DeadlineArgs args = Parser.parseDeadlineArgs(command);
            tasks.add(new Deadline(args.description(), args.by()));
            storage.save(tasks.getAll());
            ui.showAddedTask(tasks.get(tasks.size() - 1), tasks.size());
        } else if (command.equals("event") || command.startsWith("event ")) {
            Parser.EventArgs args = Parser.parseEventArgs(command);
            tasks.add(new Event(args.description(), args.from(), args.to()));
            storage.save(tasks.getAll());
            ui.showAddedTask(tasks.get(tasks.size() - 1), tasks.size());
        } else {
            throw new TBladeException("I don't know that command. Use: todo, deadline, event, list, mark, unmark, delete, or bye.");
        }
        return true;
    }

    /**
     * Starts the TBlade application, persisting tasks to {@code data/duke.txt}.
     *
     * @param args command-line arguments, which are not used by this application
     */
    public static void main(String[] args) {
        new TBlade("data/duke.txt").run();
    }
}
