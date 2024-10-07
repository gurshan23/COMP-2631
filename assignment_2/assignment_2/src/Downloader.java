import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

/**
 * Arif, someone who has a computer science background, helped me implement this class.
 *
 * This class is used to create a CSV file which contains all of the winning outcomes of the rock
 * paper scissors game.
 *
 * @author Gurshan Chera
 */

public class Downloader {
    private static final String ALL_Choices_URL = "https://rps101.pythonanywhere.com/api/v1/objects/all";

    /**
     * This method works on reading the choices from the provided Gson file and passing
     * that on to another method so it can be converted to a CSV file.
     */
    public static void downloadChoices(String outputFilePath)
    {

        List<String> allObjects = null;

        try (Reader reader = new InputStreamReader(new URI(ALL_Choices_URL).toURL().openStream()))
        {

            Gson gson = new Gson();


            java.lang.reflect.Type listType = TypeToken.getParameterized(List.class, String.class).getType();

            allObjects = gson.fromJson(reader,listType);
            System.out.println("Fetching list of Choices");

            //Convert the download to the CSV format

            System.out.println("Choices downloaded, proceeding to the CSV conversion method");

            convertToCSV(allObjects, outputFilePath); //Method to convert all downloads more easily


        }
        catch (IOException | URISyntaxException ex)
        {
            System.out.println("Error fetching game choices: " + ex.getMessage());
            System.exit(-1);
        }

    }

    /**
     * This method stores the choices in a CSV file.
     */
    public static void convertToCSV(List<String> data, String filePath) {

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath)))
        {
            writer.write("Choices\n");
            for (int i = 0; i < data.size(); i++) {
                writer.write(data.get(i) + "\n");
            }

        }

        catch (IOException ex) {
            System.out.println("Error writing to CSV: " + ex.getMessage());
            System.exit(-1);


        }
    }

    /**
     * This method writes the possible outcomes to a file as a CSV.
     */
    public static void downloadOutcomes(List<String> allChoices, String outputFilePath) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFilePath))) {
            writer.write("Winner,Action,Loser\n");

            for (String choice : allChoices) {
                String objectUrl = String.format("https://rps101.pythonanywhere.com/api/v1/objects/%s",
                        choice.replace(" ", "%20"));

                try (InputStreamReader reader = new InputStreamReader(new URI(objectUrl).toURL().openStream())) {
                    Gson gson = new Gson();
                    ObjectWins wins = gson.fromJson(reader, ObjectWins.class);

                    // Write outcomes to CSV
                    for (List<String> outcome : wins.winningOutcomes) {
                        writer.write(choice + "," + outcome.get(0) + "," + outcome.get(1) + "\n");
                    }
                } catch (IOException | URISyntaxException ex) {
                    System.out.println("Error fetching outcomes for " + choice + ": " + ex.getMessage());
                    System.exit(-1);
                }
            }

            System.out.println("Writing all outcomes to '" + outputFilePath + "'... done.");

        } catch (IOException ex) {
            System.out.println("Error writing to CSV: " + ex.getMessage());
            System.exit(-1);
        }
    }

    private static class ObjectWins {
        @SerializedName("winning outcomes")
        public List<List<String>> winningOutcomes;
    }

}

