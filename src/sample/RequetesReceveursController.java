package sample;

import classes.PopulateApplicationData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import classes.DemandeSang;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class RequetesReceveursController implements Initializable {

    @FXML
    private TableColumn<DemandeSang, LocalDateTime> demande_a_col;

    @FXML
    private TableColumn<DemandeSang, String> demande_par_col;

    @FXML
    private TableColumn<DemandeSang, LocalDate> date_demande_col;

    @FXML
    private TableColumn<DemandeSang, String> groupe_sanguin_col;

    @FXML
    private TableColumn<DemandeSang, String> type_sang_col;

    @FXML
    private TableColumn<DemandeSang, String> objectifs_col;

    @FXML
    private TableView<DemandeSang> requetesReceveurs_table;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        demande_a_col.setCellValueFactory(new PropertyValueFactory<>("cree_a"));
        demande_par_col.setCellValueFactory(new PropertyValueFactory<>("demande_par"));
        date_demande_col.setCellValueFactory(new PropertyValueFactory<>("date_demande"));
        groupe_sanguin_col.setCellValueFactory(new PropertyValueFactory<>("groupe_sanguin"));
        type_sang_col.setCellValueFactory(new PropertyValueFactory<>("type_sang"));
        objectifs_col.setCellValueFactory(new PropertyValueFactory<>("objectifs"));

        requetesReceveurs_table.setItems(PopulateApplicationData.requetesReceveurs);
    }
}
