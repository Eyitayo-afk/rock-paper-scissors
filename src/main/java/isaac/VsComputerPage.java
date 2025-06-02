package isaac;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VsComputerPage {
    public static void show(Stage stage, String username, int winTarget) {
        Label welcomeLabel = new Label("Welcome to Rock-Paper-Scissors, " + username + "!");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 16px;");

        Label scoreLabel = new Label("Score: You 0 - 0 Computer");
        scoreLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        int[] userScore = {0};
        int[] computerScore = {0};

        Button rockButton = new Button("Rock");
        Button paperButton = new Button("Paper");
        Button scissorsButton = new Button("Scissors");

        Button playAgainButton = new Button("Play Again");
        playAgainButton.setVisible(false);
        playAgainButton.setOnAction(e -> {
            userScore[0] = 0;
            computerScore[0] = 0;
            scoreLabel.setText("Score: You 0 - 0 Computer");
            resultLabel.setText("");
            rockButton.setDisable(false);
            paperButton.setDisable(false);
            scissorsButton.setDisable(false);
            playAgainButton.setVisible(false);
        });

        rockButton.setOnAction(e -> playRound("Rock", resultLabel, scoreLabel, userScore, computerScore, rockButton, paperButton, scissorsButton, playAgainButton, winTarget));
        paperButton.setOnAction(e -> playRound("Paper", resultLabel, scoreLabel, userScore, computerScore, rockButton, paperButton, scissorsButton, playAgainButton, winTarget));
        scissorsButton.setOnAction(e -> playRound("Scissors", resultLabel, scoreLabel, userScore, computerScore, rockButton, paperButton, scissorsButton, playAgainButton, winTarget));

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> GamePage.showGamePage(stage, username));

        Button goBackButton = new Button("Go Back");
        goBackButton.setOnAction(e -> GamePage.showGamePage(stage, username));
        VBox root = new VBox(20, welcomeLabel, scoreLabel, rockButton, paperButton, scissorsButton, resultLabel, playAgainButton, goBackButton, logoutButton);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 30;");

        Scene scene = new Scene(root, 400, 450);
        stage.setScene(scene);
        stage.setTitle("RPS Game - Play vs Computer");
        stage.show();
    }

    // Overload for backward compatibility
    public static void show(Stage stage, String username) {
        Label welcomeLabel = new Label("Welcome to Rock-Paper-Scissors, " + username + "!");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label modeLabel = new Label("Select Game Mode:");
        modeLabel.setStyle("-fx-font-size: 16px;");
        Button bestOf3Button = new Button("Best of 3");
        Button bestOf5Button = new Button("Best of 5");
        VBox modeBox = new VBox(10, modeLabel, bestOf3Button, bestOf5Button);
        modeBox.setAlignment(Pos.CENTER);

        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 16px;");

        Label scoreLabel = new Label("Score: You 0 - 0 Computer");
        scoreLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        int[] userScore = {0};
        int[] computerScore = {0};
        int[] winTarget = {3}; // default to best of 3

        Button rockButton = new Button("Rock");
        Button paperButton = new Button("Paper");
        Button scissorsButton = new Button("Scissors");
        rockButton.setVisible(false);
        paperButton.setVisible(false);
        scissorsButton.setVisible(false);

        Button playAgainButton = new Button("Play Again");
        playAgainButton.setVisible(false);
        playAgainButton.setOnAction(e -> {
            userScore[0] = 0;
            computerScore[0] = 0;
            scoreLabel.setText("Score: You 0 - 0 Computer");
            resultLabel.setText("");
            rockButton.setDisable(false);
            paperButton.setDisable(false);
            scissorsButton.setDisable(false);
            playAgainButton.setVisible(false);
        });

        rockButton.setOnAction(e -> playRound("Rock", resultLabel, scoreLabel, userScore, computerScore, rockButton, paperButton, scissorsButton, playAgainButton, winTarget[0]));
        paperButton.setOnAction(e -> playRound("Paper", resultLabel, scoreLabel, userScore, computerScore, rockButton, paperButton, scissorsButton, playAgainButton, winTarget[0]));
        scissorsButton.setOnAction(e -> playRound("Scissors", resultLabel, scoreLabel, userScore, computerScore, rockButton, paperButton, scissorsButton, playAgainButton, winTarget[0]));

        bestOf3Button.setOnAction(e -> {
            winTarget[0] = 3;
            modeBox.setVisible(false);
            rockButton.setVisible(true);
            paperButton.setVisible(true);
            scissorsButton.setVisible(true);
            scoreLabel.setText("Score: You 0 - 0 Computer");
            resultLabel.setText("");
        });
        bestOf5Button.setOnAction(e -> {
            winTarget[0] = 5;
            modeBox.setVisible(false);
            rockButton.setVisible(true);
            paperButton.setVisible(true);
            scissorsButton.setVisible(true);
            scoreLabel.setText("Score: You 0 - 0 Computer");
            resultLabel.setText("");
        });

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> GamePage.showGamePage(stage, username));

        Button goBackButton = new Button("Go Back");
        goBackButton.setOnAction(e -> GamePage.showGamePage(stage, username));
        VBox root = new VBox(20, welcomeLabel, modeBox, scoreLabel, rockButton, paperButton, scissorsButton, resultLabel, playAgainButton, goBackButton, logoutButton);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 30;");

        Scene scene = new Scene(root, 400, 500);
        stage.setScene(scene);
        stage.setTitle("RPS Game - Play vs Computer");
        stage.show();
    }

    private static void playRound(String userChoice, Label resultLabel, Label scoreLabel, int[] userScore, int[] computerScore, Button rockButton, Button paperButton, Button scissorsButton, Button playAgainButton, int winTarget) {
        String[] choices = {"Rock", "Paper", "Scissors"};
        String computerChoice = choices[(int)(Math.random() * 3)];
        String result;
        if (userChoice.equals(computerChoice)) {
            result = "Draw! Both chose " + userChoice + ".";
        } else if ((userChoice.equals("Rock") && computerChoice.equals("Scissors")) ||
                   (userChoice.equals("Paper") && computerChoice.equals("Rock")) ||
                   (userChoice.equals("Scissors") && computerChoice.equals("Paper"))) {
            result = "You Win! You chose " + userChoice + ", Computer chose " + computerChoice + ".";
            userScore[0]++;
        } else {
            result = "You Lose! You chose " + userChoice + ", Computer chose " + computerChoice + ".";
            computerScore[0]++;
        }
        scoreLabel.setText("Score: You " + userScore[0] + " - " + computerScore[0] + " Computer");
        resultLabel.setText(result);

        if (userScore[0] == winTarget || computerScore[0] == winTarget) {
            rockButton.setDisable(true);
            paperButton.setDisable(true);
            scissorsButton.setDisable(true);
            playAgainButton.setVisible(true);
            if (userScore[0] == winTarget) {
                resultLabel.setText(result + "\nCongratulations! You are the winner!");
            } else {
                resultLabel.setText(result + "\nComputer wins! Better luck next time.");
            }
        }
    }
}
