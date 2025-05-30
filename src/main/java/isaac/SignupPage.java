package isaac;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class SignupPage {

    private final StudentModel studentModel = new StudentModel();

    public void show(Stage stage) {
        TextField fullnameField = new TextField();
        fullnameField.setPromptText("Full Name");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Passcode (digits only)");

        Label messageLabel = new Label();

        Button signupButton = new Button("Sign Up");
        signupButton.setOnAction(e -> {
            String fullname = fullnameField.getText().trim();
            String username = usernameField.getText().trim();
            String passcodeStr = passwordField.getText().trim();

            if (fullname.isEmpty() || username.isEmpty() || passcodeStr.isEmpty()) {
                messageLabel.setText("All fields are required.");
                messageLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            try {
                int passcode = Integer.parseInt(passcodeStr);

                // Check if username already exists
                if (studentModel.getAllUsername().contains(username)) {
                    messageLabel.setText("Username already exists. Choose another.");
                    messageLabel.setStyle("-fx-text-fill: red;");
                    return;
                }

                // Add the new student
                studentModel.add(fullname, username, passcode);
                messageLabel.setText("Sign up successful! Redirecting to login...");
                messageLabel.setStyle("-fx-text-fill: green;");

                // Clear fields
                fullnameField.clear();
                usernameField.clear();
                passwordField.clear();

                // Redirect to login after short delay
                new Thread(() -> {
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException ignored) {}
                    javafx.application.Platform.runLater(() -> new LoginPage().show(stage));
                }).start();

            } catch (NumberFormatException ex) {
                messageLabel.setText("Passcode must be a number.");
                messageLabel.setStyle("-fx-text-fill: red;");
            }
        });

        Button goToLogin = new Button("Back to Login");
        goToLogin.setOnAction(e -> new LoginPage().show(stage));

        VBox root = new VBox(10, fullnameField, usernameField, passwordField, signupButton, goToLogin, messageLabel);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-alignment: center;");

        Scene scene = new Scene(root, 400, 350);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Sign Up - RPS Game");
        stage.show();
    }
}
