package Kalaha;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Scoreboard {
    
    // the scoreboard class could implement the SaveHandler interface, but the load and save methods need different inputs

    private List<List<String>> scoreBoardList;

    //--------------------- SAVING AND LOADING -------------------------

    //method for saving completed games
    //The scoreboard file is stored in the resources project folder, in the subfolder "Scoreboard/"
    // "src/main/resources/Kalaha/Saves/"
    public void scoreBoardSave(String file, Game game) throws FileNotFoundException {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(getFile(file), true))) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = new Date();
            
            bw.write(game.getPlayer1() +";"+ game.getPlayer1Score() +";"+ game.getPlayer2() +";"+ game.getPlayer2Score() +";"+dateFormat.format(date) + "\n");
            
            bw.flush();
            bw.close();

        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    public void scoreBoardLoad(String file) throws FileNotFoundException {
        try(Scanner scanner = new Scanner(getFile(file))) {

            List<List<String>> scoreList = new ArrayList<>();
            
            while (scanner.hasNextLine()) {
                String[] savedata = scanner.nextLine().split(";");
                scoreList.add(Arrays.asList(savedata));

                System.out.println(scoreList);
            }
        
        this.scoreBoardList = scoreList;
        scanner.close();
        }
    }

    // -------------------------- METHODS FOR GUI LOGIC ---------------------

    //getter for scoreBoardList
    public List<String> getScoreBoardListString() {

        //gets the scoreboard and converts it into a string for the listview in the controller
        List<String> scoreBoardString = new ArrayList<String>();

        for (List<String> list : scoreBoardList) {
            scoreBoardString.add(list.toString());
        }

        return scoreBoardString;
    }

    //Source: https://stackoverflow.com/questions/35761864/java-sort-list-of-lists
    //methods for sorting the list from the controller/scoreboard GUI
    public void sortScoreBoardByPlayer1() {
        Collections.sort(scoreBoardList, new SortScoreBoardByName<>());
    }

    public void sortScoreBoardByTime() {
        Collections.sort(scoreBoardList, new SortScoreBoardByDate<>());
    }

    // --------------------- SUPPORTING METHODS ---------------------------

    private static File getFile(String filename) {
        return new File("src/main/resources/Kalaha/Scoreboard/" + filename + ".txt");
    }

    //method for finding correct path during testing
    public Path getScoreBoardPath(String filename) {
        return Path.of("src/main/resources/Kalaha/Scoreboard/" + filename + ".txt");
    }


}