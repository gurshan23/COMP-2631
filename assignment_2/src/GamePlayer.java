import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class is used to determine the winner between the choices chosen by player 1 and player 2
 * from a CSV file. It also determines the appropriate verb that should be placed among the choices,
 * among other things.
 *
 * @author Gurshan Chera
 */
public class GamePlayer {
    private static final String File = "allOutcomes.csv";
    private static String[][] outcomesArray;
    private static String[] choicesArray;

    /**
     * This method reads the CSV file, asks for user input, and calls other methods within this class to determine
     * if user input is applicable.
     */
    public static void reading() {
        // Step 1: Load the CSV file and populate the outcomes matrix
        csvOutcomes(File);

        // Step 2: Read input from players and determine the winner
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Let's play RPS-101!");

            // Get Player 1's choice
            System.out.print("Player 1, what object do you choose? ");
            // trim() helps us to avoid having to account for leading and trailing
            // whitespace from user input.
            String player1Choice = scanner.nextLine().trim();
            int player1Index = getChoiceIndex(player1Choice);
            if (player1Index == -1) {
                System.out.println("I'm sorry, Player 1, but '" + player1Choice + "' is not "
                        + "a valid object in RPS-101.");
                return;
            }

            // Get Player 2's choice
            System.out.print("Player 2, what object do you choose? ");
            String player2Choice = scanner.nextLine().trim();
            int player2Index = getChoiceIndex(player2Choice);
            if (player2Index == -1) {
                System.out.println("I'm sorry, Player 2, but '" + player2Choice + "' is not a "
                        + "valid object in RPS-101.");
                return;
            }

            // Step 3: Determine the outcome and display it
            displayOutcome(player1Choice, player2Choice, player1Index, player2Index);
        }
    }

    /**
     * This method help to populate a 2D array with elements from the CSV file.
     */
    public static void csvOutcomes(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String header = reader.readLine();
            choicesArray = header.split(",");
            // Need to subtract one from array length to account for first empty block.
            int numChoices = choicesArray.length - 1;
            outcomesArray = new String[numChoices][numChoices];

            // Read each row and populate the outcomes matrix
            String line;
            while ((line = reader.readLine()) != null) {
                // -1 keeps the empty spots found in the original file.
                // Isolates the first choice (air) and fills the rest of the array
                // with the filler verbs.
                String[] values = line.split(",", -1);
                String winner = values[0]; // Winner choice is the first element

                // Find the index of the winner
                int winnerIndex = getChoiceIndex(winner);
                if (winnerIndex == -1) {
                    continue; // Skip if the winner is invalid (shouldn't happen)
                }

                // Populate the outcomes matrix for this winner
                for (int i = 1; i < values.length; i++) { // Skip first column (winner's name)
                    if (!values[i].isEmpty()) {
                        outcomesArray[winnerIndex][i - 1] = values[i]; // Store verb in matrix
                    }
                }
            }

            System.out.println("Reading all objects from 'allOutcomes.csv'... done.");
            System.out.println("Reading all outcomes from 'allOutcomes.csv'... done.");
        }
        catch (IOException readFile) {
            System.err.println("Error reading CSV file: " + readFile.getMessage());
            System.exit(-1);
        }
    }

    /**
     * This method determines the index of the choices array.
     */
    public static int getChoiceIndex(String choice) {
        // Search for the choice in the choicesArray and return its index.
        for (int i = 1; i < choicesArray.length; i++) { // Start from index 1 (skip empty first column)
            if (choicesArray[i].equalsIgnoreCase(choice)) {
                return i - 1; // Return index adjusted to fit outcomesArray.
            }
        }
        return -1; // Choice not found
    }

    /**
     * This method displays the outcomes from the choices selected by player 1 and player 2.
     */
    public static void displayOutcome(String player1Choice, String player2Choice,
            int player1Index, int player2Index) {
        // Check who wins using the matrix
        if (outcomesArray[player1Index][player2Index] != null) {
            System.out.println("Player 1 wins: " + capitalizeChoice(player1Choice) + " "
                    + outcomesArray[player1Index][player2Index] + " " + capitalizeChoice(player2Choice));
        }
        else if (outcomesArray[player2Index][player1Index] != null) {
            System.out.println("Player 2 wins: " + capitalizeChoice(player2Choice) + " "
                    + outcomesArray[player2Index][player1Index] + " " + capitalizeChoice(player1Choice));
        }
        else {
            System.out.println("It's a tie!");
        }
    }

    public static String capitalizeChoice(String choice) {
        if (choice == null || choice.isEmpty()) {
            return "";
        }
        return choice.substring(0, 1).toUpperCase() + choice.substring(1).toLowerCase();
    }
}