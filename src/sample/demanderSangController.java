package sample;
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
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class demanderSangController implements Initializable {
    private String connected_user_fromUserView;


    @FXML
    private TextArea objectif_field;

    @FXML
    private JFXComboBox<String> combo_type_sang;

    @FXML
    private DatePicker datePicker;

    @FXML
    private JFXButton demanderSang_button;

    @FXML
    private JFXButton retour_button;

    @FXML
    private Label erreur1;

    @FXML
    private Label erreur2;

    @FXML
    private Label erreur3;

    @FXML
    void back(ActionEvent event) {
            ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void enregistrerDemandeSang(ActionEvent event){
        if(combo_type_sang.getValue() == null) erreur1.setVisible(true);
        else if (datePicker.getValue() == null) {erreur1.setVisible(false); erreur2.setVisible(true);}
        else if(objectif_field.getText().isEmpty()) {erreur1.setVisible(false); erreur2.setVisible(false); erreur3.setVisible(true);}
        else {
            erreur3.setVisible(false);
        try{ //On doit d'abord retrouver le groupe du donneur
            ResultSet rs = Main.getConnexion().retrieveData(String.format("SELECT * FROM table_donneurs WHERE username='%s'",
                    this.connected_user_fromUserView));
            if(rs.next())
            {
                String GS = rs.getString("groupe_sanguin");
                PreparedStatement prepstmt = Main.getConnexion().getConnection().
                prepareStatement("INSERT INTO table_demandes(demande_par,date_demande,groupe_sanguin,objectifs,type_sang) VALUES(?,?,?,?,?,?)");
                int nbre_insertion = prepstmt.executeUpdate(String.format("INSERT INTO table_demandes(demande_par,date_demande,groupe_sanguin,objectifs,type_sang)" +
                        "VALUES('%s','%s','%s','%s','%s')",this.connected_user_fromUserView,datePicker.getValue().toString(),GS,objectif_field.getText(),
                        combo_type_sang.getValue()));
                System.out.println(nbre_insertion + " insertion faite avec succès. (TABLE_DEMANDEURS)");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        finally
        {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Demande de sang enregistré");
            alert.setHeaderText(null);
            alert.setContentText("Votre demande a été bien été prise en considération!");
            alert.showAndWait();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        }
        }

    }

    public void setConnected_user_fromUserView(String connected_user_fromUserView) {
        this.connected_user_fromUserView = connected_user_fromUserView;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combo_type_sang.setItems(FXCollections.observableArrayList("Sang phénotypé","Plaquettes de sang","Globules rouges","Plasma"));
    }
}
