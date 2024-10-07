import java.nio.file.Paths;

/**
 * Assignment 2 main file.
 *
 * @author Gurshan Chera
 */
public class Main {

    /**
     * Where we are calling our classes and different functions from.
     */
    public static void main(String[] args) {
        //String choicesFilePath =  "D:\\Programming\\Programming_Projects\\Git-Repos\\COMP-2631\\assignment_2\\API_FILES\\choices2.csv";
        //String choices = "/Users/gurshanchera/eclipse-workspace/assignment_2/all.json";
        
    	// Step 1: Set up the dynamic base path
        String basePath = Paths.get("").toAbsolutePath().toString();
        System.out.println("base path before " + basePath);

        // Append the missing folder (assignment_2) for Windows
        basePath = Paths.get(basePath, "assignment_2").toString();
        System.out.println("base path after " + basePath);

        // Set up the file paths for choices and outcomes using Windows-style path
        String choicesFilePath = basePath + "\\API_FILES\\choices2.csv";  
        String outcomesFilePath = basePath + "\\API_FILES\\outcomes2.csv";

        // Step 2: Download choices and outcomes, and write them to the CSV file
        System.out.println("Step 1: Downloading choices and outcomes...");
        Downloader.downloadChoicesAndOutcomes(outcomesFilePath);  // This will generate the CSV file for outcomes

        // Step 3: Output message that the process is completed successfully
        System.out.println("Step 2: Process completed, outcomes written to: " + outcomesFilePath);
    
        //GamePlayer.fileLocations(choicesFilePath, outcomesFilePath);
          GamePlayer.reading();
    }
}