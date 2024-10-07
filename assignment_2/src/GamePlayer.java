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

    //private static final String File = "allOutcomes.csv";

    /**
     * This method just reiterates where the choices and outcomes files were located.
     */
    /*public static void fileLocations(String choicesFile, String outcomesFile) {
        //File = outcomesFile;
        System.out.println("Choices CSV Path: " + choicesFile);
        System.out.println("Outcomes CSV Path: " + outcomesFile);
        System.out.println();
        // Further logic to handle the files
    }*/

    /**
     * This method reads the CSV file, asks for user input, and calls other methods within this class to determine
     * if user input is applicable.
     */

    private static final String File = "D:\\Programming\\Programming_Projects\\Git-Repos\\COMP-2631\\assignment_2\\API_FILES\\outcomes2.csv";
    private static String[][] outcomesArray;
    private static String[] choicesArray;

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
            
            int numChoices = choicesArray.length - 1; // Adjust for the empty first column
            outcomesArray = new String[numChoices][numChoices];

            // Read each row and populate the outcomes matrix
            String line;
            int rowCount = 0;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",", -1);  // Read the row and split by comma
                String winner = values[0];  // First element is the winner
                
                // Find the index of the winner
                int winnerIndex = getChoiceIndex(winner);
                if (winnerIndex == -1) {
                    System.out.println("Skipping invalid winner: " + winner);
                    continue;  // Skip invalid entries
                }

                rowCount++;  // Increment the row count for debugging

                // Populate the outcomes matrix for this winner
                for (int i = 1; i < values.length; i++) {
                    if (!values[i].isEmpty()) {
                        if (winnerIndex < outcomesArray.length && (i - 1) < outcomesArray[winnerIndex].length) {
                            outcomesArray[winnerIndex][i - 1] = values[i];  // Store the action verb
                        } else {
                            System.out.println("Warning: Index out of bounds at row " + rowCount + ", column " + i);
                        }
                    }
                }
            }

            System.out.println("Reading all objects from '" + fileName + "'... done.");
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            System.exit(-1);
        }
    }


    /**
     * This method determines the index of the choices array.
     */
    public static int getChoiceIndex(String choice) {
        // Search for the choice in the choicesArray and return its index.
        for (int i = 0; i < choicesArray.length; i++) {  // Start from index 0
            if (choicesArray[i].trim().equalsIgnoreCase(choice.trim())) {  // Case-insensitive match
                return i - 1;  // Return index adjusted to fit outcomesArray.
            }
        }
        return -1;  // Choice not found
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

    /**
     * This function capitalizes the first letter of the choice and makes the other letters lower case.
     */
    public static String capitalizeChoice(String choice) {
        if (choice == null || choice.isEmpty()) {
            return "";
        }
        return choice.substring(0, 1).toUpperCase() + choice.substring(1).toLowerCase();
    }
}

