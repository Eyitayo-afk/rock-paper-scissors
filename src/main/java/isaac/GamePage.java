package isaac;




import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class GamePage {

    public static void showGamePage(Stage stage, String username) {
        Label welcomeLabel = new Label("Welcome to Rock-Paper-Scissors, " + username + "!");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label modeLabel = new Label("Choose Game Mode:");
        Button pvpButton = new Button("Player vs Player");
        Button pvcButton = new Button("Player vs Computer");
        Button logoutButton = new Button("Logout"); // Add logout button

        VBox modeBox = new VBox(10, modeLabel, pvpButton, pvcButton);
        modeBox.setAlignment(Pos.CENTER);

        VBox root = new VBox(20, welcomeLabel, modeBox, logoutButton); // Add logout button to root
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 30;");
        Scene scene = new Scene(root, 400, 450);
        scene.getStylesheets().add(GamePage.class.getResource("/isaac/game.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("RPS Game - Play");
        stage.show();

        pvpButton.setOnAction(e -> VsPlayerPage.show(stage, username));
        pvcButton.setOnAction(e -> VsComputerPage.show(stage, username));
        logoutButton.setOnAction(e -> new LoginPage().show(stage)); // Show login page on logout
    }
}
