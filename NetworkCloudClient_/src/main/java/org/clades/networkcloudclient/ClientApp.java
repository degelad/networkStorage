package org.clades.networkcloudclient;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX ClientApp
 */
public class ClientApp extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
//    public void start(Stage primaryStage) throws IOException {
        scene = new Scene(loadFXML("primary"), 380, 340);
        stage.setTitle("Lan chat");
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/css/Styles.css").toExternalForm());
//    Parent root = FXMLLoader.load(getClass().getResource("client.fxml"));
//    primaryStage.setTitle("NetworkCloud");
//    primaryStage.setScene(new Scene(root, 400, 400));
//    primaryStage.show();
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApp.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}