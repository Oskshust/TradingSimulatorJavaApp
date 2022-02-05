package com.example.tradingsimapp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * Main class that starts the Application
 *
 * @author OskarSzudzik
 */
public class UserDisplay extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UserDisplay.class.getResource("exchange-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 757);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });

        stage.setTitle("Welcome in the world of big money and even bigger credits!");
        stage.setScene(scene);
        stage.show();
    }

}