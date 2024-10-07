import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

public class Downloader {

    // The base URL for all choices and outcomes
    private static final String ALL_CHOICES_URL = "https://rps101.pythonanywhere.com/api/v1/objects/all";
    private static final String OUTCOME_URL_TEMPLATE = "https://rps101.pythonanywhere.com/api/v1/objects/%s";

    /**
     * Method to download the choices and write the outcomes to a CSV file.
     */
    public static void downloadChoicesAndOutcomes(String outputFilePath) {
        List<String> allChoices = downloadChoices();

        if (allChoices == null || allChoices.isEmpty()) {
            System.out.println("Error: No choices were downloaded.");
            System.exit(-1);
        }

        // Initialize a matrix to store outcomes
        String[][] outcomesMatrix = new String[allChoices.size() + 1][allChoices.size() + 1];
        outcomesMatrix[0][0] = "";  // Top-left corner is empty

        // Set header row and first column with choices
        for (int i = 0; i < allChoices.size(); i++) {
            outcomesMatrix[0][i + 1] = allChoices.get(i);  // Header row
            outcomesMatrix[i + 1][0] = allChoices.get(i);  // First column
        }

        // Fetch outcomes for each choice and fill the matrix
        for (int i = 0; i < allChoices.size(); i++) {
            String choice = allChoices.get(i);
            ObjectWins outcomes = downloadOutcomes(choice);

            if (outcomes != null) {
                for (List<String> outcome : outcomes.winningOutcomes) {
                    String action = outcome.get(0);
                    String loser = outcome.get(1);

                    int loserIndex = allChoices.indexOf(loser);
                    if (loserIndex != -1) {
                        outcomesMatrix[i + 1][loserIndex + 1] = action;
                    }
                }
            }
        }

        // Write the matrix to the CSV file
        writeToCSV(outputFilePath, outcomesMatrix);
    }

    /**
     * Downloads the list of all choices from the API.
     */
    private static List<String> downloadChoices() {
        List<String> allChoices = null;

        try (Reader reader = new InputStreamReader(new URI(ALL_CHOICES_URL).toURL().openStream())) {
            Gson gson = new Gson();
            java.lang.reflect.Type listType = TypeToken.getParameterized(List.class, String.class).getType();
            allChoices = gson.fromJson(reader, listType);
        } catch (IOException | URISyntaxException ex) {
            System.out.println("Error fetching game choices: " + ex.getMessage());
            System.exit(-1);
        }

        return allChoices;
    }

    /**
     * Downloads the winning outcomes for a single choice from the API.
     */
    private static ObjectWins downloadOutcomes(String choice) {
        String objectUrl = String.format(OUTCOME_URL_TEMPLATE, choice.replace(" ", "%20"));
        ObjectWins wins = null;

        try (Reader reader = new InputStreamReader(new URI(objectUrl).toURL().openStream())) {
            Gson gson = new Gson();
            wins = gson.fromJson(reader, ObjectWins.class);
        } catch (IOException | URISyntaxException ex) {
            System.out.println("Error fetching outcomes for " + choice + ": " + ex.getMessage());
        }

        return wins;
    }

    /**
     * Writes the outcomes matrix to a CSV file.
     */
    private static void writeToCSV(String outputFilePath, String[][] outcomesMatrix) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFilePath))) {
            for (String[] row : outcomesMatrix) {
                writer.write(String.join(",", row));
                writer.newLine();
            }
            System.out.println("Outcomes written to '" + outputFilePath + "' successfully.");
        } catch (IOException ex) {
            System.out.println("Error writing to CSV: " + ex.getMessage());
            System.exit(-1);
        }
    }

    // Inner class to store outcomes data from the API
    private static class ObjectWins {
        @SerializedName("winning outcomes")
        public List<List<String>> winningOutcomes;
    }
}
