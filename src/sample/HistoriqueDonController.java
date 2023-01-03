package sample;

import classes.HistoriqueDon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class HistoriqueDonController implements Initializable {

    @FXML
    private TableView<HistoriqueDon> historique_table;

    @FXML
    private TableColumn<HistoriqueDon, LocalDate> date_don_col;

    @FXML
    private TableColumn<HistoriqueDon, String> GS_col;

    @FXML
    private TableColumn<HistoriqueDon, String> type_sang_col;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList historique_list = FXCollections.observableArrayList();

        {
            try {

                ResultSet rs = Main.getConnexion().retrieveData("SELECT * FROM historique_don;");
                while (rs.next()) {
                    historique_list.add(new HistoriqueDon(rs.getDate(1).toLocalDate(), rs.getString(2), rs.getString(3)));

                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                date_don_col.setCellValueFactory(new PropertyValueFactory<>("date_don"));
                GS_col.setCellValueFactory(new PropertyValueFactory<>("groupe_sanguin"));
                type_sang_col.setCellValueFactory(new PropertyValueFactory<>("type_sang"));

                historique_table.setItems(historique_list);
            }

        }
    }
}
