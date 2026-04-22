package ui;

import java.util.Scanner;

/**
 * The ConsoleUI class encapsulates all standard input and output operations.
 * By centralizing I/O, the core engine is decoupled from the specific console implementation,
 * making it easier to swap interfaces (e.g., to a graphical GUI) in the future.
 */
public class ConsoleUI {
    private Scanner scanner;

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Outputs a standard message to the user.
     * @param message The text to display.
     */
    public void printMessage(String message) {
        System.out.println(message);
    }

    /**
     * Outputs an error or system alert to the user.
     * @param error The error message to display.
     */
    public void printError(String error) {
        System.out.println("\n[!] " + error);
    }

    /**
     * Prints a formatted header for room transitions, adopting the classic text-adventure aesthetic.
     * @param roomName The name of the current room.
     */
    public void printRoomHeader(String roomName) {
        System.out.println("\n==[ " + roomName.toUpperCase() + " ]==");
    }

    /**
     * Prompts the user for input, trims whitespace, and converts to lowercase for uniform parsing.
     * @return The sanitized user input string.
     */
    public String getUserInput() {
        System.out.print("\n> ");
        return scanner.nextLine().trim().toLowerCase();
    }
}