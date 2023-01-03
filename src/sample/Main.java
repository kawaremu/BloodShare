package sample;

import classes.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("../resources/login.fxml"));
        primaryStage.getIcons().add(new Image("/img/blood-bag.png"));
        primaryStage.setTitle("Banque de sang");
        primaryStage.setScene(new Scene(root, 717, 438));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setResizable(false);
        primaryStage.show();
        new PopulateApplicationData();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static Connexion getConnexion()
    {
        return new Connexion();
    }
}

