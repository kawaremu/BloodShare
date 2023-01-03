package sample;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AdminViewController {
    private FXMLLoader loader;
    private Parent root;

    @FXML
    private JFXButton consulterStock_button;

    @FXML
    private JFXButton requests_button;

    @FXML
    private JFXButton donneurs_button;

    @FXML
    private JFXButton historique_button;

    @FXML
    private JFXButton logout_button;

    @FXML
    private JFXButton collecte_sang_button;

    @FXML
    private JFXButton creer_activite_button;

    @FXML
    void consulterRequetes(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../resources/requete_receveurs.fxml"));
            Stage stage = new Stage();
            stage.getIcons().add(new Image("/img/impatient.png"));
            stage.setTitle("Banque de sang");
            stage.setScene(new Scene(root, 852, 350));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void consulterStockSang(ActionEvent event) {

        try {
            loader = new FXMLLoader(getClass().getResource("../resources/stock_sang.fxml"));
            root = loader.load();
            StockSangController stocksang_controller = loader.getController();
            Stage stage = new Stage();
            stage.getIcons().add(new Image("/img/blood-sample.png"));
            stage.setTitle("Consultation du stock de sang");
            stage.setScene(new Scene(root, 916,675));
            //stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void enregistrerDon(ActionEvent event) {
        try {
            loader = new FXMLLoader(getClass().getResource("../resources/ajouterDon.fxml"));
            root = loader.load();
            ajouterDonController ajouterDon_controller = loader.getController();
            Stage stage = new Stage();
            stage.getIcons().add(new Image("/img/blood-bag-collecte.png"));
            stage.setTitle("Enregistrement de don");
            stage.setScene(new Scene(root, 562, 488));
            stage.setResizable(false);
            stage.show();
            //((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void logout(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../resources/login.fxml"));
            Stage stage = new Stage();
            stage.getIcons().add(new Image("/img/blood-bag.png"));
            stage.setTitle("Banque de sang");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root, 717, 438));
            stage.setResizable(false);
            stage.show();
            // Hide this current window
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void ouvrir_historique_dons(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../resources/historique_dons.fxml"));
            Stage stage = new Stage();
            stage.getIcons().add(new Image("/img/scroll.png"));
            stage.setTitle("Historique des dons");
            stage.setScene(new Scene(root, 618, 431));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void voirRepertoireDonneurs(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../resources/repertoire_donneurs.fxml"));
            Stage stage = new Stage();
            stage.getIcons().add(new Image("/img/blood-pressure.png"));
            stage.setTitle("Répertoire des donneurs");
            stage.setScene(new Scene(root, 989, 637));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void ouvrirCreationActivite(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../resources/creer_activite.fxml"));
            Stage stage = new Stage();
            stage.getIcons().add(new Image("/img/juggler.png"));
            stage.setTitle("Création d'une activité");
            stage.setScene(new Scene(root, 500, 390));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}