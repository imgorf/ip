import java.util.Scanner;

/**
 * Runs the TBlade command-line application.
 */
public class TBlade {
    private static final String PIG = " 🐷";

    /**
     * Displays a greeting, echoes user commands, and exits when the user enters {@code bye}.
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
        while (true) {
            String command = scanner.nextLine();
            printLine(separator);

            if (command.equals("bye")) {
                printLine("Bye. Hope to see you again soon!");
                printLine(separator);
                break;
            }

            printLine(" " + command);
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
