/*import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

/**
 * This class is used to create a CSV file which contains all of the winning outcomes of the rock
 * paper scissors game.
 *
 * @author Gurshan Chera


public class Downloader {

    private static final String ALL_OBJECTS_URL = "https://rps101.pythonanywhere.com/api/v1/objects/all";
    private static final String OBJECT_OUTCOMES_URL_TEMPLATE = "https://rps101.pythonanywhere.com/api/v1/objects/%s";
    private static String[][] outcomesMatrix;
    private static List<String> allChoices;

    public static void main(String[] args) {
        try {
            // Step 1: Download and store all game choices
            allChoices = downloadAllChoices();
            if (allChoices == null || allChoices.isEmpty()) {
                System.err.println("Failed to retrieve choices from the API.");
                System.exit(-1);
            }

            System.out.println("Getting all objects... done.");

            // Step 2: Initialize the outcomes matrix and download outcomes for each choice
            outcomesMatrix = new String[allChoices.size()][allChoices.size()];
            for (String choice : allChoices) {
                downloadAndStoreOutcomes(choice);
            }

            System.out.println("Getting all outcomes... done.");

            // Step 3: Write outcomes to CSV
            writeOutcomesToCsv("allOutcomes.csv");
            System.out.println("Writing all outcomes to 'allOutcomes.csv'... done.");
        } catch (IOException | URISyntaxException error) {
            System.err.println("Error: " + error.getMessage());
            System.exit(-1);
        }
    }


    private static List<String> downloadAllChoices() throws IOException, URISyntaxException {
        List<String> choices = null;
        try (Reader reader = new InputStreamReader(new URI(ALL_OBJECTS_URL).toURL().openStream())) {
            Gson gson = new Gson();
            choices = gson.fromJson(reader, new TypeToken<List<String>>() {}.getType());
        }
        return choices;
    }

    private static void downloadAndStoreOutcomes(String choice) throws IOException, URISyntaxException {
        // Create the URL for a specific object's outcomes
        String objectUrl = String.format(OBJECT_OUTCOMES_URL_TEMPLATE, choice.replace(" ", "%20"));
        ObjectWins objectWins = null;

        try (Reader reader = new InputStreamReader(new URI(objectUrl).toURL().openStream())) {
            Gson gson = new Gson();
            objectWins = gson.fromJson(reader, ObjectWins.class);
        }

        // If no outcomes are found, skip this object
        if (objectWins == null || objectWins.winningOutcomes == null) {
            return;
        }

        // Populate the outcomesMatrix using the outcomes for this choice
        int winnerIndex = allChoices.indexOf(choice);
        for (List<String> outcome : objectWins.winningOutcomes) {
            String verb = outcome.get(0);
            String loser = outcome.get(1);
            int loserIndex = allChoices.indexOf(loser);

            if (winnerIndex != -1 && loserIndex != -1) {
                outcomesMatrix[winnerIndex][loserIndex] = verb.replace(",", ";"); // Replace commas with semicolons
            }
        }
    }

    private static void writeOutcomesToCsv(String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            // Write header row
            writer.append(",");
            for (String choice : allChoices) {
                writer.append(choice).append(",");
            }
            writer.append("\n");

            // Write each row for the matrix
            for (int i = 0; i < allChoices.size(); i++) {
                writer.append(allChoices.get(i)).append(","); // First column is the choice itself
                for (int j = 0; j < allChoices.size(); j++) {
                    if (outcomesMatrix[i][j] != null) {
                        writer.append(outcomesMatrix[i][j]);
                    }
                    writer.append(",");
                }
                writer.append("\n");
            }
            //System.out.println(writer);
        }
    }

    // Class to hold the JSON structure of winning outcomes
    private static class ObjectWins {
        @SerializedName("winning outcomes")
        public List<List<String>> winningOutcomes;
    }
} */

