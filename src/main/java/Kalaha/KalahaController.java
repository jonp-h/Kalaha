package Kalaha;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class KalahaController {

    private Game game;
    private Scoreboard scoreboard;

    @FXML
    private GridPane background;

    @FXML
    private Label info, stoneLabel, feedBackLabel, labelAI, scoreBoardLabel, sortLabel;

    @FXML
    private TextField player1Name, player2Name, loadInput;

    @FXML
    private TextArea playRules;

    @FXML
    private CheckBox playerStarting;

    @FXML
    private ListView<String> scoreBoardList;

    @FXML
    private Pane informationCover;

    // Source: https://community.oracle.com/tech/developers/discussion/2486012/fxml-combobox-created-in-scene-builder-how-to-fetch-data-from-database
    @FXML
    private ComboBox<Integer> startingStones;

    @FXML
    private ComboBox<String> versusAI;

    @FXML
    private Button scoreBoardButton, sortPlayer1, sortTime, startGame, loadButton, saveButton;

    @FXML
    private Button home6, home13;

    @FXML
    private Button hole0, hole1, hole2, hole3, hole4, hole5, hole7, hole8, hole9, hole10, hole11, hole12;

    @FXML
    private void initialize() {
        
        // initializing game object that will be overwritten when player loads or starts game
        game = new Game("playerOne", "playerTwo", true, 6, "Human");
        scoreboard = new Scoreboard();

        // initializes options for ComboBoxes
        startingStones.getItems().addAll(4,5,6);
        startingStones.getSelectionModel().select(2);
        versusAI.getItems().addAll("Human","Easy", "Medium");
        versusAI.getSelectionModel().select(0);
    }

    // ------------------ GUI ELEMENTS -------------------

    //Main method for starting a game
    @FXML
    private void startGame() {
        try {
            game = new Game(player1Name.getText(), player2Name.getText(), playerStarting.isSelected(), startingStones.getValue(), versusAI.getValue());
        } catch (Exception e) {
            feedBackLabel.setText("Name can only contain letters and spaces!");
            return;
        }
        
        hole0.setDisable(false); hole1.setDisable(false); hole2.setDisable(false); hole3.setDisable(false); hole4.setDisable(false); hole5.setDisable(false); 
        hole7.setDisable(false); hole8.setDisable(false); hole9.setDisable(false); hole10.setDisable(false); hole11.setDisable(false); hole12.setDisable(false); 
        
        showPlaying();
        updateHoles();
        player1Name.setVisible(false);
        player2Name.setVisible(false);
        labelAI.setVisible(false);
        versusAI.setVisible(false);
        playerStarting.setVisible(false);
        stoneLabel.setVisible(false);
        startingStones.setVisible(false);
        startGame.setVisible(false);
        saveButton.setVisible(true);
    } 

    @FXML
    private void loadGame() {
        SaveHandler saveHandler = new SaveHandler();
        try {
            saveHandler.readSave(loadInput.getText(), game);
            System.out.println("vellykket opplastning");
            // game = new Game(player1Name.getText(), player2Name.getText(), playerStarting.isSelected(), startingStones.getValue());
            //update view
               
            hole0.setDisable(false); hole1.setDisable(false); hole2.setDisable(false); hole3.setDisable(false); hole4.setDisable(false); hole5.setDisable(false); 
            hole7.setDisable(false); hole8.setDisable(false); hole9.setDisable(false); hole10.setDisable(false); hole11.setDisable(false); hole12.setDisable(false); 
            
            updateHoles();         
            updateScore();
            System.out.println("Riktig innlastning av view");
            feedBackLabel.setText("Loaded!");

            showPlaying();
            updateHoles();
            player1Name.setVisible(false);
            player2Name.setVisible(false);
            labelAI.setVisible(false);
            versusAI.setVisible(false);
            playerStarting.setVisible(false);
            stoneLabel.setVisible(false);
            startingStones.setVisible(false);
            startGame.setVisible(false);
            saveButton.setVisible(true);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            feedBackLabel.setText("No such file!");
            e.printStackTrace();
            System.out.println("feil i opplastning");
        } catch (IllegalArgumentException e) {
            feedBackLabel.setText("Cannot load corrupted file");
            e.printStackTrace();
            System.out.println("feil i opplastning");
        }
    }

    @FXML
    private void saveGame() {
        SaveHandler saveHandler = new SaveHandler();
        try {
            saveHandler.writeSave(loadInput.getText(), game);
            System.out.println("vellykket lagring");
            feedBackLabel.setText("Saved!");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            feedBackLabel.setText(e.getMessage());
            e.printStackTrace();
            System.out.println("feil i lagring");
        }
    }

    @FXML
    private void showScoreBoard() {
        if (scoreBoardList.isVisible() == false) {
            informationCover.setVisible(true);
            info.setVisible(false); scoreBoardList.setVisible(true); scoreBoardLabel.setVisible(true); sortLabel.setVisible(true); sortPlayer1.setVisible(true); sortTime.setVisible(true);
            hole0.setVisible(false); hole1.setVisible(false); hole2.setVisible(false); hole3.setVisible(false); hole4.setVisible(false); hole5.setVisible(false); home6.setVisible(false); 
            hole7.setVisible(false); hole8.setVisible(false); hole9.setVisible(false); hole10.setVisible(false); hole11.setVisible(false); hole12.setVisible(false); home13.setVisible(false);
            try {
                scoreboard.scoreBoardLoad("scoreboard");
                scoreBoardList.getItems().setAll(scoreboard.getScoreBoardListString());
            } catch (IOException e) {
                info.setVisible(true);
                info.setText("Scoreboard file does not exist! Play a game first!");
                e.printStackTrace();
            }

        } else {
            informationCover.setVisible(false);
            info.setVisible(false); scoreBoardList.setVisible(false); scoreBoardLabel.setVisible(false); sortLabel.setVisible(false); sortPlayer1.setVisible(false); sortTime.setVisible(false);
            hole0.setVisible(true); hole1.setVisible(true); hole2.setVisible(true); hole3.setVisible(true); hole4.setVisible(true); hole5.setVisible(true); home6.setVisible(true); 
            hole7.setVisible(true); hole8.setVisible(true); hole9.setVisible(true); hole10.setVisible(true); hole11.setVisible(true); hole12.setVisible(true); home13.setVisible(true);

        }
    }

    @FXML
    private void sortByPlayer1() {
        scoreboard.sortScoreBoardByPlayer1();
        scoreBoardList.getItems().setAll(scoreboard.getScoreBoardListString());
        System.out.println("sortert!");
    }

    @FXML
    private void sortByTime() {
        scoreboard.sortScoreBoardByTime();
        scoreBoardList.getItems().setAll(scoreboard.getScoreBoardListString());
        System.out.println("sortert!");
    }

    @FXML
    private void showPlayRules() {
        if (playRules.isVisible() == false) {
            playRules.setVisible(true); informationCover.setVisible(true);
            hole0.setVisible(false); hole1.setVisible(false); hole2.setVisible(false); hole3.setVisible(false); hole4.setVisible(false); hole5.setVisible(false); home6.setVisible(false); 
            hole7.setVisible(false); hole8.setVisible(false); hole9.setVisible(false); hole10.setVisible(false); hole11.setVisible(false); hole12.setVisible(false); home13.setVisible(false);

        } else {
            playRules.setVisible(false); informationCover.setVisible(false);
            hole0.setVisible(true); hole1.setVisible(true); hole2.setVisible(true); hole3.setVisible(true); hole4.setVisible(true); hole5.setVisible(true); home6.setVisible(true); 
            hole7.setVisible(true); hole8.setVisible(true); hole9.setVisible(true); hole10.setVisible(true); hole11.setVisible(true); hole12.setVisible(true); home13.setVisible(true);
        }
    }

    // ------------------ HOLES -------------------


    @FXML
    private void hole0() {
        try {
        game.playRound(0);
        updateScore();
        updateHoles();
        } catch (Exception e) {
            feedBackLabel.setText(e.getMessage());
           }
     }

    @FXML
    private void hole1() {
        try {
        game.playRound(1);
        updateScore();
        updateHoles();
        } catch (Exception e) {
            feedBackLabel.setText(e.getMessage());
                   }
     }

    @FXML
    private void hole2() {
        try {
        game.playRound(2);
        updateScore();
        updateHoles();
        } catch (Exception e) {
            feedBackLabel.setText(e.getMessage());
                   }
     }

    @FXML
    private void hole3() {
        try {
        game.playRound(3);
        updateScore();
        updateHoles();
        } catch (Exception e) {
            feedBackLabel.setText(e.getMessage());
                   }
     }

    @FXML
    private void hole4() {
        try {
        game.playRound(4);
        updateScore();
        updateHoles();
        } catch (Exception e) {
            feedBackLabel.setText(e.getMessage());
                   }
     }

    @FXML
    private void hole5() {
        try {
        game.playRound(5);
        updateScore();
        updateHoles();
        } catch (Exception e) {
            feedBackLabel.setText(e.getMessage());
                   }
     }

    @FXML
    private void hole7() {
        try {
        game.playRound(7);
        updateScore();
        updateHoles();
        } catch (Exception e) {
            feedBackLabel.setText(e.getMessage());
                   }
     }

    @FXML
    private void hole8() {
        try {
        game.playRound(8);
        updateScore();
        updateHoles();
        } catch (Exception e) {
            feedBackLabel.setText(e.getMessage());
                   }
     }

    @FXML
    private void hole9() {
        try {
        game.playRound(9);
        updateScore();
        updateHoles();
        } catch (Exception e) {
            feedBackLabel.setText(e.getMessage());
                   }
     }

    @FXML
    private void hole10() {
    try {
        game.playRound(10);
        updateScore();
        updateHoles();
        } catch (Exception e) {
            feedBackLabel.setText(e.getMessage());
                    }

    }

    @FXML
    private void hole11() {
    try {
        game.playRound(11);
        updateScore();
        updateHoles();
        } catch (Exception e) {
            feedBackLabel.setText(e.getMessage());
                    }

    }

    @FXML
    private void hole12() { 
    try {
        game.playRound(12);
        updateScore();
        updateHoles();
        } catch (Exception e) {
            feedBackLabel.setText(e.getMessage());
                    }

    }

    // ---------------------- SUPPORTING METHODS FOR UPDATING GUI ---------------------

    private void updateScore() {
        home6.setText(game.getPlayer1Score());
        home13.setText(game.getPlayer2Score());
        showPlaying();
        handleGameOver(); 
    }

    private void showPlaying() {
        info.setVisible(true);
        if (game.getBoard().getPlayerPlaying() == true) {
            info.setText("It is " + game.getPlayer1() + "'s turn!");
            info.setStyle("-fx-background-color: green;");
        } else {
            info.setText("It is " + game.getPlayer2() + "'s turn!");
            info.setStyle("-fx-background-color: red;");
        }
    }

    private void handleGameOver() {
        if (game.getGameOver() == true) {
            //disable buttons if game is over
            hole0.setDisable(true); hole1.setDisable(true); hole2.setDisable(true); hole3.setDisable(true); hole4.setDisable(true); hole5.setDisable(true); 
            hole7.setDisable(true); hole8.setDisable(true); hole9.setDisable(true); hole10.setDisable(true); hole11.setDisable(true); hole12.setDisable(true); 
            info.setStyle("-fx-background-color: yellow;");

            //post winner and save game to scoreboard
            if (Integer.parseInt(game.getPlayer1Score()) > Integer.parseInt(game.getPlayer2Score())) {
                info.setText("The game is over! " + game.getPlayer1() + " won!");
                try {
                    scoreboard.scoreBoardSave("scoreboard", game);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    feedBackLabel.setText("Error saving to scoreboard");
                }
            } else if (Integer.parseInt(game.getPlayer1Score()) == Integer.parseInt(game.getPlayer2Score())) {
                info.setText("The game is over! It is a draw!");
                try {
                    scoreboard.scoreBoardSave("scoreboard", game);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    feedBackLabel.setText("Error saving to scoreboard");
                }
            }
             else {
                info.setText("The game is over! " + game.getPlayer2() + " won!");
                try {
                    scoreboard.scoreBoardSave("scoreboard", game);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    feedBackLabel.setText("Error saving to scoreboard");
                }
            }
        }
        
    }

    private void updateHoles() {
        feedBackLabel.setText("");
        hole0.setText(String.valueOf(game.getBoard().getStones(0)));
        hole1.setText(String.valueOf(game.getBoard().getStones(1)));
        hole2.setText(String.valueOf(game.getBoard().getStones(2)));
        hole3.setText(String.valueOf(game.getBoard().getStones(3)));
        hole4.setText(String.valueOf(game.getBoard().getStones(4)));
        hole5.setText(String.valueOf(game.getBoard().getStones(5)));

        hole7.setText(String.valueOf(game.getBoard().getStones(7)));
        hole8.setText(String.valueOf(game.getBoard().getStones(8)));
        hole9.setText(String.valueOf(game.getBoard().getStones(9)));
        hole10.setText(String.valueOf(game.getBoard().getStones(10)));
        hole11.setText(String.valueOf(game.getBoard().getStones(11)));
        hole12.setText(String.valueOf(game.getBoard().getStones(12)));
    }





}
