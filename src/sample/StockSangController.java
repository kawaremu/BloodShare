package sample;
import classes.Don;
import classes.PopulateApplicationData;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class StockSangController implements Initializable {

    @FXML
    private JFXComboBox<String> combo_type_sang;

    @FXML
    private JFXButton fitrer_button;

    @FXML
    private Label compteur_type_sang;

    @FXML
    private TableView<Don> table_stock_sang;

    @FXML
    private TableColumn<Don, Integer> id_don_col;

    @FXML
    private TableColumn<Don, String> nom_donneur_col;

    @FXML
    private TableColumn<Don, String> groupe_sanguin_col;

    @FXML
    private TableColumn<Don, String> type_sang_col;

    @FXML
    private TableColumn<Don, LocalDate> date_collecte_col;

    @FXML
    private TableColumn<Don, String> medecin_col;

    @FXML
    private TableColumn<Don, String> hopital_col;

    @FXML
    private JFXButton detruire_perime_button;

    @FXML
    private Label erreur_champ;

    @FXML
    void detruireStockPerime(ActionEvent event) {
        try {
            Statement stmt = Main.getConnexion().getConnection().createStatement();
            int lignes_supprimees = stmt.executeUpdate("DELETE FROM table_dons WHERE DATEDIFF(UTC_DATE(),date_collecte)>15;");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Stock de sang mis à jour");
            alert.setHeaderText(null);
            if(lignes_supprimees != 0) alert.setContentText(lignes_supprimees +" dons de sang périmés ont été détruits.");
            else alert.setContentText("Aucun don n'est périmé pour l'instant.");
            alert.showAndWait();
        }catch (SQLException e){
            e.printStackTrace();
        }
        table_stock_sang.getItems().clear();
        PopulateApplicationData.updateDataDons();
        table_stock_sang.setItems(PopulateApplicationData.stockSang);

    }

    @FXML
    void filtrerParType(ActionEvent event) {
        if(combo_type_sang.getValue() == null) erreur_champ.setVisible(true);
        else {
            erreur_champ.setVisible(false);
            int compteur = 0;
            try {
                ResultSet rs = Main.getConnexion().retrieveData(String.format("SELECT COUNT(*) FROM table_dons WHERE type_sang='%s'", combo_type_sang.getValue()));
                while (rs.next()) {
                    compteur = rs.getInt(1);
                }
                compteur_type_sang.setText(String.valueOf(compteur));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combo_type_sang.setItems(FXCollections.observableArrayList("Sang phénotypé", "Plaquettes de sang", "Globules rouges", "Plasma"));

        id_don_col.setCellValueFactory(new PropertyValueFactory<>("ID_DON"));
        groupe_sanguin_col.setCellValueFactory(new PropertyValueFactory<>("groupe_sanguin"));
        nom_donneur_col.setCellValueFactory(new PropertyValueFactory<>("nom_donneur"));
        type_sang_col.setCellValueFactory(new PropertyValueFactory<>("type_sang"));
        date_collecte_col.setCellValueFactory(new PropertyValueFactory<>("dateCollecte"));
        medecin_col.setCellValueFactory(new PropertyValueFactory<>("medecin_responsable"));
        hopital_col.setCellValueFactory(new PropertyValueFactory<>("nom_hopital"));

        table_stock_sang.setItems(PopulateApplicationData.stockSang);
    }

}