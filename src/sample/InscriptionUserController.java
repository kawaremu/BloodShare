package sample;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class InscriptionUserController {
    private String chosen_username,chosen_password;
    private FXMLLoader loader;
    private Parent root;

    @FXML
    private Pane paneSignUp;

    @FXML
    private TextField username_field_signup;

    @FXML
    private JFXButton signUp_button;

    @FXML
    private JFXButton back_button;

    @FXML
    private PasswordField password_field;

    @FXML
    private Label error_username;

    @FXML
    private Label empty_password_label;

    public Label getError_username() {
        return error_username;
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        try{
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
    } catch (
    IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    void continuerInscription(ActionEvent event) {
        try {
            PreparedStatement prepstmt = Main.getConnexion().getConnection().prepareStatement("INSERT INTO table_users(username,password) VALUES(?,?)");
            int nbre_insertion = prepstmt.executeUpdate(String.format("INSERT INTO table_users(username,password) VALUES('%s','%s')",
                    this.chosen_username,
                    this.chosen_password));
            System.out.println(nbre_insertion + " insertion faite avec succès. (TABLE_USERS)");

            loader = new FXMLLoader(getClass().getResource("../resources/inscription_donneur.fxml"));
            root = loader.load();
            InscriptionDonneurController donneur_controller = loader.getController();
            //On transfert au controlleur de l'interface de renseignements infos Donneurs la variable username pour inserer selon les CI
            donneur_controller.setUsername_from_signup(this.chosen_username);
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 791, 532));
            stage.setTitle("Renseignements sur le donneur : "+ this.chosen_username);
            stage.getIcons().add(new Image("/img/sign-up.png"));
            stage.setResizable(false);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();


        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }


    public TextField getUsername_field_signup() {
        return username_field_signup;
    }

    public JFXButton getSignUp_button() {
        return signUp_button;
    }

    public PasswordField getPassword_field() {
        return password_field;
    }

    public Label getEmptyPasswordError() {
        return empty_password_label;
    }

    public void setChosen_username(String chosen_username) {
        this.chosen_username = chosen_username;
    }

    public void setChosen_password(String chosen_password) {
        this.chosen_password = chosen_password;
    }
}

