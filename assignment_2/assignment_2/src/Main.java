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
        String filePath = "/Users/gurshanchera/eclipse-workspace/assignment_2/A2.rtf";
        String choices = "/Users/gurshanchera/eclipse-workspace/assignment_2/all.json";
        String outcomes = "/Users/gurshanchera/eclipse-workspace/assignment_2/Video Game.json";
        Downloader download = new Downloader();
        download.downloadChoices(filePath);






        GamePlayer.fileLocations(choices, outcomes);
        GamePlayer.reading();
    }
}