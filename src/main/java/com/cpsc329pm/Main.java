package com.cpsc329pm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The entry point of the JavaFX application.
 * This class loads the initial login scene and starts the JavaFX application lifecycle.
 */
public class Main extends Application {

    /**
     * The main entry point for all JavaFX applications.
     * Loads the LoginScene.fxml and displays the login window.
     *
     * @param primaryStage the primary stage for this application, onto which
     *        the application scene can be set.
     * @throws IOException if the FXML file cannot be loaded
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Load the login scene from FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));

        // Create a new scene with the loaded FXML layout
        Scene scene = new Scene(fxmlLoader.load());

        // Set the scene on the primary stage (main window)
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login Page"); // Set window title
        primaryStage.show(); // Show the login window
    }

    /**
     * The main method that launches the JavaFX application.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}
