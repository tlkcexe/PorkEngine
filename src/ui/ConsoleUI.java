package ui;

import java.util.Scanner;

public class ConsoleUI {
    private Scanner scanner;

    public ConsoleUI() {
        // Αρχικοποιούμε τον Scanner για να διαβάζει από το πληκτρολόγιο
        this.scanner = new Scanner(System.in);
    }

    // Μέθοδος για να τυπώνουμε απλά μηνύματα
    public void printMessage(String message) {
        System.out.println(message);
    }

    // Μέθοδος για να τυπώνουμε σφάλματα (ίσως με άλλο χρώμα στο μέλλον)
    public void printError(String error) {
        System.err.println(error);
    }

    // Μέθοδος που περιμένει τον παίκτη να γράψει κάτι και το επιστρέφει
    public String getUserInput() {
        System.out.print("> "); // Τυπώνουμε το "βέλος" του prompt (όπως στο Zork)
        return scanner.nextLine().trim().toLowerCase(); // Διαβάζουμε και καθαρίζουμε το κείμενο
    }
}