package sample;

import classes.PopulateApplicationData;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ajouterDonController implements Initializable {

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

    @FXML
    private Label erreur_champ6;


    @FXML
    private JFXComboBox<String> combo_type_sang;

    @FXML
    private DatePicker datePicker;

    @FXML
    private JFXButton ajouterDon_button;

    @FXML
    private JFXComboBox<String> combo_nom_hopital;

    @FXML
    private TextField nom_donneur_field;

    @FXML
    private TextField responsable_field;

    @FXML
    private JFXComboBox<String> combo_GS;

    @FXML
    private TextField username_field;

    @FXML
    void ajouterDon(ActionEvent event) {
        if (this.getNom_donneur_field().getText().isEmpty()) erreur_champ1.setVisible(true);
        else if (this.getResponsable_field().getText().isEmpty()) erreur_champ2.setVisible(true);
        else if (this.getCombo_GS().getValue() == null) erreur_champ3.setVisible(true);
        else if (this.getCombo_type_sang().getValue() == null) erreur_champ4.setVisible(true);
        else if (this.getCombo_nom_hopital().getValue() == null) erreur_champ5.setVisible(true);
        else if (this.getDatePicker().getValue() == null) erreur_champ6.setVisible(true);

        else {
            if (this.getUsername_field().getText().isEmpty())  //On insère le don sans le champ username
            {
                try {
                    PreparedStatement prepstmt = Main.getConnexion().getConnection().
                            prepareStatement("INSERT INTO table_dons(nom_complet,groupe_sanguin,type_sang,date_collecte,medecin_responsable,hopital) VALUES(?,?,?,?,?,?)");

                    int nbre_insertion = prepstmt.executeUpdate(String.format("INSERT INTO table_dons(nom_complet,groupe_sanguin,type_sang,date_collecte,medecin_responsable,hopital)" +
                                    "VALUES('%s','%s','%s','%s','%s','%s')", this.getNom_donneur_field().getText(), this.getCombo_GS().getValue(), this.getCombo_type_sang().getValue(),
                            this.getDatePicker().getValue().toString(), this.getResponsable_field().getText(), this.getCombo_nom_hopital().getValue()));
                    System.out.println(nbre_insertion + " insertion faite avec succès. (TABLE_DONS)");
                    PopulateApplicationData.updateDataDons();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Don enregistré");
                    alert.setHeaderText(null);
                    alert.setContentText("Le don a été enregistré avec succès. 🆗");
                    alert.showAndWait();
                    ((Node) (event.getSource())).getScene().getWindow().hide();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else //Le donneur a déjà un compte et vient enregistrer son don
            {
                try {
                    /*D'abord, on insère le don dans la table_dons avec le champ username*/
                    PreparedStatement prepstmt = Main.getConnexion().getConnection().
                   prepareStatement("INSERT INTO table_dons(username,nom_complet,groupe_sanguin,type_sang,date_collecte,medecin_responsable,hopital) VALUES(?,?,?,?,?,?,?)");

                    int nbre_insertion = prepstmt.executeUpdate(String.format("INSERT INTO table_dons(username,nom_complet,groupe_sanguin,type_sang,date_collecte,medecin_responsable,hopital)" +
                                    "VALUES('%s','%s','%s','%s','%s','%s','%s')", this.getUsername_field().getText(),this.getNom_donneur_field().getText(), this.getCombo_GS().getValue(), this.getCombo_type_sang().getValue(),
                            this.getDatePicker().getValue().toString(), this.getResponsable_field().getText(), this.getCombo_nom_hopital().getValue()));
                    /* Ensuite, on met à jour le nombre de dons faits par ce user dans la table_donneurs*/
                    Statement stmt = Main.getConnexion().getConnection().createStatement();
                    stmt.executeUpdate(String.format("UPDATE table_donneurs SET nombre_don = nombre_don+1 WHERE username='%s'",this.getUsername_field().getText()));
                    System.out.println(nbre_insertion + " insertion et modification faite avec succès. (TABLE_DONS)");
                    PopulateApplicationData.updateDataDonneurs();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Don enregistré");
                    alert.setHeaderText(null);
                    alert.setContentText("Le don a été enregistré avec succès. 🆗");
                    alert.showAndWait();
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combo_GS.setItems(FXCollections.observableArrayList("A+", "A-", "AB+", "B+", "B-", "O+", "O-"));
        combo_type_sang.setItems(FXCollections.observableArrayList("Sang phénotypé", "Plaquettes de sang", "Globules rouges", "Plasma"));
        ArrayList hopitaux = new ArrayList<String>();
        try {
            ResultSet rs = Main.getConnexion().retrieveData("SELECT nom_hopital FROM table_hopitaux;");
            while (rs.next()) {
                hopitaux.add(rs.getString("nom_hopital"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        combo_nom_hopital.setItems(FXCollections.observableArrayList(hopitaux));
    }


    public JFXComboBox<String> getCombo_type_sang() {
        return combo_type_sang;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public JFXButton getAjouterDon_button() {
        return ajouterDon_button;
    }

    public JFXComboBox<String> getCombo_nom_hopital() {
        return combo_nom_hopital;
    }

    public TextField getNom_donneur_field() {
        return nom_donneur_field;
    }

    public TextField getResponsable_field() {
        return responsable_field;
    }

    public JFXComboBox<String> getCombo_GS() {
        return combo_GS;
    }

    public TextField getUsername_field() {
        return username_field;
    }
}
