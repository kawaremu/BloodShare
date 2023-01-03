package sample;

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
import javafx.scene.control.TextArea;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreationActiviteController implements Initializable {

    @FXML
    private JFXComboBox<String> combo_type_activite;

    @FXML
    private DatePicker datePicker;

    @FXML
    private JFXComboBox<String> combo_nom_hopital;

    @FXML
    private Label erreur_champ2;

    @FXML
    private Label erreur_champ3;

    @FXML
    private Label erreur_champ1;

    @FXML
    private TextArea details_fiels;

    @FXML
    private JFXButton generer_activite_button;

    @FXML
    void ajouterActivite(ActionEvent event)
    {
        if(combo_type_activite.getValue() == null) erreur_champ1.setVisible(true);
        else if(combo_nom_hopital.getValue() == null) {erreur_champ1.setVisible(false); erreur_champ2.setVisible(true);}
        else if(datePicker.getValue() == null){erreur_champ1.setVisible(false); erreur_champ2.setVisible(false); erreur_champ3.setVisible(true);}
        else
        {
            erreur_champ1.setVisible(false);
            erreur_champ2.setVisible(false);
            erreur_champ3.setVisible(false);

            try {
                PreparedStatement prepstmt = Main.getConnexion().getConnection().
                        prepareStatement("INSERT INTO table_activites(date_activite,place_activite,type_activite,details_activite) VALUES(?,?,?,?)");

                int nbre_insertion = prepstmt.executeUpdate(String.format("INSERT INTO table_activites(date_activite,place_activite,type_activite,details_activites) " +
                        "VALUES('%s','%s','%s','%s')",datePicker.getValue().toString(),combo_nom_hopital.getValue(),combo_type_activite.getValue(),details_fiels.getText()));
                System.out.println(nbre_insertion + " insertion faite avec succès. (TABLE_ACTIVITES)");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Création d'activé");
                alert.setHeaderText(null);
                alert.setContentText("Les utilisateurs ont été informés avec succès!");
                alert.showAndWait();
                ((Node) (event.getSource())).getScene().getWindow().hide();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        ArrayList hopitaux = new ArrayList<String> ();
        try {
            ResultSet rs = Main.getConnexion().retrieveData("SELECT nom_hopital FROM table_hopitaux;");
            while (rs.next()) {
                hopitaux.add(rs.getString("nom_hopital"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        combo_nom_hopital.setItems(FXCollections.observableArrayList(hopitaux));
        combo_type_activite.setItems(FXCollections.observableArrayList("Sensibilisation","Collecte de sang","Distribution de sang"));
    }
}
