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
        String filePath =  "choices2.csv";
        //String choices = "/Users/gurshanchera/eclipse-workspace/assignment_2/all.json";
        String outcomesFile = "outcomes2.csv";
        Downloader download = new Downloader();

        System.out.println("Step 1");
        //download.downloadChoices(filePath);
        System.out.println("Step 2");

        //download.downloadOutcomes(filePath, outcomesFile);


        //GamePlayer.fileLocations(filePath, outcomesFile);
        GamePlayer.reading();
    }
}