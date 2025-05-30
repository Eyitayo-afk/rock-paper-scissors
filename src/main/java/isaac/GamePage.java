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

        // A simple button to logout and return to login page
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> new LoginPage().show(stage));

        VBox root = new VBox(20, welcomeLabel, logoutButton);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 30;");

        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.setTitle("RPS Game - Play");
        stage.show();
    }
}
