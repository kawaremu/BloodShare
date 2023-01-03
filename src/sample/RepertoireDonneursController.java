package sample;
import classes.PopulateApplicationData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import classes.Donneur;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class RepertoireDonneursController implements Initializable {

    @FXML
    private TableView<Donneur> repertoireDonneurs_table;

    @FXML
    private TableColumn<Donneur, String> nom_col;

    @FXML
    private TableColumn<Donneur, String> adresse__col;

    @FXML
    private TableColumn<Donneur, String> mail_col;

    @FXML
    private TableColumn<Donneur, String> groupe_sanguin_col;

    @FXML
    private TableColumn<Donneur, Integer> age_col;

    @FXML
    private TableColumn<Donneur, String> genre_col;

    @FXML
    private TableColumn<Donneur, Integer> nbre_don_col;

    @FXML
    private TableColumn<Donneur, String> remarques_col;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            nom_col.setCellValueFactory(new PropertyValueFactory<>("nom"));
           adresse__col.setCellValueFactory(new PropertyValueFactory<>("adresse"));
           mail_col.setCellValueFactory(new PropertyValueFactory<>("email"));
           age_col.setCellValueFactory(new PropertyValueFactory<>("age"));
           genre_col.setCellValueFactory(new PropertyValueFactory<>("genre"));
           groupe_sanguin_col.setCellValueFactory(new PropertyValueFactory<>("groupeSanguin"));
           remarques_col.setCellValueFactory(new PropertyValueFactory<>("remarques"));
           nbre_don_col.setCellValueFactory(new PropertyValueFactory<>("nombre_don"));

           repertoireDonneurs_table.setItems(PopulateApplicationData.repertoireDonneurs);

    }
}
