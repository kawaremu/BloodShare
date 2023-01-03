package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class faireDonController implements Initializable {
    private String connected_user_fromUserView;

    public void setConnected_user_fromUserView(String connected_user_fromUserView) {
        this.connected_user_fromUserView = connected_user_fromUserView;
    }

    @FXML
    private JFXComboBox<String> combo_type_sang;

    @FXML
    private DatePicker datePicker;

    @FXML
    private JFXButton faireDon_button;

    @FXML
    private JFXComboBox<String> combo_nom_hopital;


    @FXML
    private JFXButton retour_button;
    @FXML
    private Label erreur1;

    @FXML
    private Label erreur2;

    @FXML
    private Label erreur3;


    public void initialize(URL location, ResourceBundle resources) {
        ArrayList hopitaux = new ArrayList<String> ();
        try {
            ResultSet rs = Main.getConnexion().retrieveData("SELECT nom_hopital FROM table_hopitaux;");
            while (rs.next()) {
                hopitaux.add(rs.getString("nom_hopital"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        combo_type_sang.setItems(FXCollections.observableArrayList("Sang phénotypé","Plaquettes de sang","Globules rouges","Plasma"));
        combo_nom_hopital.setItems(FXCollections.observableArrayList(hopitaux));
    }


    @FXML
    void enregitrerDonUser(ActionEvent event) {
        if(combo_type_sang.getValue() == null) erreur1.setVisible(true);
        else if (combo_nom_hopital.getValue() == null) {erreur1.setVisible(false); erreur2.setVisible(true);}
        else if(datePicker.getValue() == null) {erreur1.setVisible(false); erreur2.setVisible(false); erreur3.setVisible(true);}
        else {
            try {
                //Ajout du don dans la table_dons
                ResultSet rs = Main.getConnexion().retrieveData(String.format("SELECT * FROM table_donneurs WHERE username='%s'",
                        this.connected_user_fromUserView));
                if (rs.next())
                {
                    String nom = rs.getString(2);
                    String GS = rs.getString(7);
                    int nbre_dons = rs.getInt("nombre_don");
                    PreparedStatement prepstmt = Main.getConnexion().getConnection().
                            prepareStatement("INSERT INTO table_dons(username,nom_complet,groupe_sanguin,type_sang,date_collecte,hopital) VALUES(?,?,?,?,?,?)");

                    if(nbre_dons >= 6)
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Vous êtes TROP généreux! ");
                        alert.setHeaderText("Vous êtes un peu trop généreux...");
                        alert.setContentText("Vous avez donné plus de 6 fois cette année! Reposez vous un peu.");
                        alert.showAndWait();
                    }
                    else
                        {
                        int nbre_insertion = prepstmt.executeUpdate(String.format("INSERT INTO table_dons(username,nom_complet,groupe_sanguin,type_sang,date_collecte,hopital) " +
                                        "VALUES('%s','%s','%s','%s','%s','%s')", this.connected_user_fromUserView, nom, GS, this.combo_type_sang.getValue(), this.datePicker.getValue().toString(),
                                this.combo_nom_hopital.getValue()));

                        //Modification dans la table_donneurs, on met à jour son nombre de dons

                        Statement stmt = Main.getConnexion().getConnection().createStatement();
                        stmt.executeUpdate(String.format("UPDATE table_donneurs SET nombre_don = nombre_don+1 WHERE username='%s'", this.connected_user_fromUserView));
                        System.out.println(nbre_insertion + " insertion et modification faite avec succès. (TABLE_DONS)");


                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Don enregistré");
                        alert.setHeaderText(null);
                        alert.setContentText("Merci pour votre générosité! 💗");
                        alert.showAndWait();
                        }
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void back(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

}


