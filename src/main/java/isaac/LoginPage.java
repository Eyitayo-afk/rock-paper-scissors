package isaac;

import java.io.InputStream;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginPage {

    private final StudentModel studentModel = new StudentModel();

    public void show(Stage stage) {
        // Logo image
        InputStream logoStream = getClass().getResourceAsStream("/Logo.png");
        if (logoStream == null) {
            throw new RuntimeException("Logo image not found in classpath!");
        }
        Image logo = new Image(logoStream);

        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(100);
        logoView.setPreserveRatio(true);

        // Title
        Label titleLabel = new Label("RPS GAME");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Username input
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        // Passcode input
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Passcode (digits only)");

        // Message label for feedback
        Label messageLabel = new Label();
        messageLabel.setWrapText(true);

        // Login button
        Button loginButton = new Button("Login");
        loginButton.setDefaultButton(true);
        loginButton.setDisable(true); // Initially disabled

        // Enable login button only when both fields have input
        usernameField.textProperty().addListener((obs, oldText, newText) -> {
            messageLabel.setText("");
            loginButton.setDisable(newText.trim().isEmpty() || passwordField.getText().trim().isEmpty());
        });
        passwordField.textProperty().addListener((obs, oldText, newText) -> {
            messageLabel.setText("");
            loginButton.setDisable(usernameField.getText().trim().isEmpty() || newText.trim().isEmpty());
        });

        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            try {
                int passcode = Integer.parseInt(password);

                // Run DB check in a background thread
                javafx.concurrent.Task<Integer> loginTask = new javafx.concurrent.Task<>() {
                    @Override
                    protected Integer call() {
                        return studentModel.check(username, passcode);
                    }
                };

                loginTask.setOnSucceeded(ev -> {
                    int id = loginTask.getValue();
                    if (id != 0) {
                        messageLabel.setText("Login successful! Welcome, " + username);
                        messageLabel.setStyle("-fx-text-fill: green;");
                        GamePage.showGamePage(stage, username);
                    } else {
                        messageLabel.setText("Invalid username or passcode.");
                        messageLabel.setStyle("-fx-text-fill: red;");
                    }
                });

                loginTask.setOnFailed(ev -> {
                    messageLabel.setText("Login failed due to an error.");
                    messageLabel.setStyle("-fx-text-fill: red;");
                });

                new Thread(loginTask).start();

            } catch (NumberFormatException ex) {
                messageLabel.setText("Passcode must be a number.");
                messageLabel.setStyle("-fx-text-fill: red;");
            }
        });

        // Button to switch to signup page
        Button goToSignup = new Button("Sign Up");
        goToSignup.setOnAction(e -> new SignupPage().show(stage));

        // Layout container
        VBox root = new VBox(15, logoView, titleLabel, usernameField, passwordField, loginButton, goToSignup,
                messageLabel);
        root.setPadding(new Insets(25));
        root.setStyle("-fx-alignment: center;");

        // Scene setup
        Scene scene = new Scene(root, 400, 400);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Login - RPS Game");
        stage.show();
    }
}
