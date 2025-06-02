package isaac;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VsPlayerPage {
    public static void show(Stage stage, String username) {
        Label titleLabel = new Label("Player vs Player Mode");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Label scoreLabel = new Label("Score: Player 1 (You) 0 - 0 Player 2");
        scoreLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 16px;");
        int[] p1Score = {0};
        int[] p2Score = {0};
        String[] p1Choice = {null};
        String[] p2Choice = {null};
        Label turnLabel = new Label("Player 1: Choose your move");
        Button rockButton = new Button("Rock");
        Button paperButton = new Button("Paper");
        Button scissorsButton = new Button("Scissors");
        Button playAgainButton = new Button("Play Again");
        playAgainButton.setVisible(false);
        playAgainButton.setOnAction(e -> {
            p1Score[0] = 0;
            p2Score[0] = 0;
            scoreLabel.setText("Score: Player 1 (You) 0 - 0 Player 2");
            resultLabel.setText("");
            turnLabel.setText("Player 1: Choose your move");
            rockButton.setDisable(false);
            paperButton.setDisable(false);
            scissorsButton.setDisable(false);
            playAgainButton.setVisible(false);
            p1Choice[0] = null;
            p2Choice[0] = null;
        });
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> GamePage.showGamePage(stage, username));
        Button goBackButton = new Button("Go Back");
        goBackButton.setOnAction(e -> GamePage.showGamePage(stage, username));
        rockButton.setOnAction(e -> handlePvPTurn("Rock", p1Choice, p2Choice, turnLabel, resultLabel, scoreLabel, p1Score, p2Score, rockButton, paperButton, scissorsButton, playAgainButton));
        paperButton.setOnAction(e -> handlePvPTurn("Paper", p1Choice, p2Choice, turnLabel, resultLabel, scoreLabel, p1Score, p2Score, rockButton, paperButton, scissorsButton, playAgainButton));
        scissorsButton.setOnAction(e -> handlePvPTurn("Scissors", p1Choice, p2Choice, turnLabel, resultLabel, scoreLabel, p1Score, p2Score, rockButton, paperButton, scissorsButton, playAgainButton));
        VBox root = new VBox(20, titleLabel, scoreLabel, turnLabel, rockButton, paperButton, scissorsButton, resultLabel, playAgainButton, goBackButton, logoutButton);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 30;");
        Scene scene = new Scene(root, 400, 500);
        stage.setScene(scene);
        stage.setTitle("RPS Game - Player vs Player");
        stage.show();
    }

    private static void handlePvPTurn(String choice, String[] p1Choice, String[] p2Choice, Label turnLabel, Label resultLabel, Label scoreLabel, int[] p1Score, int[] p2Score, Button rockButton, Button paperButton, Button scissorsButton, Button playAgainButton) {
        if (p1Choice[0] == null) {
            p1Choice[0] = choice;
            turnLabel.setText("Player 2: Choose your move");
            resultLabel.setText("");
        } else if (p2Choice[0] == null) {
            p2Choice[0] = choice;
            // Evaluate round
            String result;
            if (p1Choice[0].equals(p2Choice[0])) {
                result = "Draw! Both chose " + p1Choice[0] + ".";
            } else if ((p1Choice[0].equals("Rock") && p2Choice[0].equals("Scissors")) ||
                       (p1Choice[0].equals("Paper") && p2Choice[0].equals("Rock")) ||
                       (p1Choice[0].equals("Scissors") && p2Choice[0].equals("Paper"))) {
                result = "Player 1 wins the round! " + p1Choice[0] + " beats " + p2Choice[0] + ".";
                p1Score[0]++;
            } else {
                result = "Player 2 wins the round! " + p2Choice[0] + " beats " + p1Choice[0] + ".";
                p2Score[0]++;
            }
            scoreLabel.setText("Score: Player 1 (You) " + p1Score[0] + " - " + p2Score[0] + " Player 2");
            resultLabel.setText(result);
            p1Choice[0] = null;
            p2Choice[0] = null;
            if (p1Score[0] == 3 || p2Score[0] == 3) {
                rockButton.setDisable(true);
                paperButton.setDisable(true);
                scissorsButton.setDisable(true);
                playAgainButton.setVisible(true);
                if (p1Score[0] == 3) {
                    resultLabel.setText(result + "\nCongratulations! Player 1 is the winner!");
                } else {
                    resultLabel.setText(result + "\nPlayer 2 wins! Better luck next time.");
                }
            } else {
                turnLabel.setText("Player 1: Choose your move");
            }
        }
    }
}
