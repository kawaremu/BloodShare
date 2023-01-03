package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.FXCollections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.print.DocFlavor;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class InscriptionDonneurController implements Initializable {
    private String username_from_signup;
    private FXMLLoader loader;
    private Parent root;

    @FXML
    private TextField nom_field;


    @FXML
    private TextField adresse_field;

    @FXML
    private TextField mail_field;

    @FXML
    private JFXComboBox<Integer> combo_date_naissance;

    @FXML
    private JFXComboBox<String> combo_genre;

    @FXML
    private JFXComboBox<String> combo_GS;

    @FXML
    private TextArea remarques_area;

    @FXML
    private JFXButton enregistrer_button;

    @FXML
    private Label erreur_champ;

    @FXML
    private Label erreur_champ1;

    @FXML
    private Label erreur_champ2;

    @FXML
    private Label erreur_champ3;

    @FXML
    private Label erreur_champ4;

    @FXML
    private Label erreur_champ5;


    public void initialize(URL location, ResourceBundle resources) {
        combo_GS.setItems(FXCollections.observableArrayList("A+","A-","AB+","B+","B-","O+","O-"));
        combo_genre.setItems(FXCollections.observableArrayList("Homme","Femme"));
        ArrayList<Integer> list_int = new ArrayList<>();
        for(int i=1920;i<=2015;i++){
            list_int.add(i);
        }
        combo_date_naissance.setItems(FXCollections.observableArrayList(list_int));
    }



    @FXML
    void enregistrerInfos(ActionEvent event) {

            if (this.getNom_field().getText().isEmpty()) erreur_champ.setVisible(true);
            else if (this.getAdresse_field().getText().isEmpty()) {erreur_champ.setVisible(false); erreur_champ1.setVisible(true);}
            else if (this.getMail_field().getText().isEmpty()) {erreur_champ.setVisible(false); erreur_champ1.setVisible(false);erreur_champ2.setVisible(true);}
            else if (this.getCombo_date_naissance().getValue() == null) {erreur_champ.setVisible(false); erreur_champ1.setVisible(false);erreur_champ2.setVisible(false);erreur_champ3.setVisible(true);}
            else if (this.getCombo_genre().getValue() == null) {
            erreur_champ.setVisible(false);
            erreur_champ1.setVisible(false);
            erreur_champ2.setVisible(false);
            erreur_champ3.setVisible(false);
            erreur_champ4.setVisible(true);}
            else if (this.getCombo_GS().getValue() == null)
            {
                erreur_champ.setVisible(false);
                erreur_champ1.setVisible(false);
                erreur_champ2.setVisible(false);
                erreur_champ3.setVisible(false);
                erreur_champ4.setVisible(false);
                erreur_champ5.setVisible(true);
            }
            else {
                erreur_champ.setVisible(false);
                erreur_champ1.setVisible(false);
                erreur_champ2.setVisible(false);
                erreur_champ3.setVisible(false);
                erreur_champ4.setVisible(false);
                erreur_champ5.setVisible(false);
                try{
                PreparedStatement prepstmt = Main.getConnexion().getConnection().
                        prepareStatement("INSERT INTO table_donneurs(username,nom,adresse,email,date_naissance,genre,groupe_sanguin,remarques) VALUES(?,?,?,?,?,?,?,?)");
                int nbre_insertion = prepstmt.executeUpdate(String.format("INSERT INTO table_donneurs(username,nom,adresse,email,date_naissance,genre,groupe_sanguin,remarques)" +
                                " VALUES('%s','%s','%s','%s','%d','%s','%s','%s')", this.username_from_signup, this.nom_field.getText(), this.adresse_field.getText(), this.mail_field.getText(), this.combo_date_naissance.getValue(),
                        this.combo_genre.getValue(), this.getCombo_GS().getValue(), this.remarques_area.getText()));
                System.out.println(nbre_insertion + " insertion faite avec succès. (TABLE_DONNEURS)");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Renseignements donneur");
                alert.setHeaderText(null);
                alert.setContentText("Vous êtes maintenant enregitré(e), reconnectez-vous!");
                alert.showAndWait();

                loader = new FXMLLoader(getClass().getResource("../resources/login.fxml"));
                root = loader.load();
                Stage stage = new Stage();
                stage.getIcons().add(new Image("/img/blood-bag.png"));
                stage.setTitle("Banque de sang");
                stage.setScene(new Scene(root, 717, 438));
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setResizable(false);
                stage.show();
                // Hide this current window
                ((Node) (event.getSource())).getScene().getWindow().hide();
            }catch(IOException | SQLException e){
                e.printStackTrace();
            }
        }
    }




    public TextField getNom_field() {
        return nom_field;
    }



    public TextField getAdresse_field() {
        return adresse_field;
    }

    public TextField getMail_field() {
        return mail_field;
    }

    public JFXComboBox<Integer> getCombo_date_naissance() {
        return combo_date_naissance;
    }

    public JFXComboBox<String> getCombo_genre() {
        return combo_genre;
    }

    public JFXComboBox<String> getCombo_GS() {
        return combo_GS;
    }

    public TextArea getRemarques_area() {
        return remarques_area;
    }

    public JFXButton getEnregistrer_button() {
        return enregistrer_button;
    }

    public void setUsername_from_signup(String username_from_signup) {
        this.username_from_signup = username_from_signup;
    }

}
