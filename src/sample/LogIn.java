package sample;

import classes.User;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogIn  {
    private FXMLLoader loader;
    private Parent root;
    @FXML
    private Pane pane_login;

    @FXML
    private TextField username_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private JFXButton login_button;

    @FXML
    private JFXButton signIn_button;

    @FXML
    private Label error_text;

    @FXML
    private Pane paneSignUp;

    @FXML
    private TextField username_field_signup;

    @FXML
    private JFXButton signUp_button;

    @FXML
    private JFXButton back_button;

    @FXML
    private JFXButton exit_button;

    @FXML
    private JFXButton reduce_button;

    @FXML
    void login(ActionEvent event) throws Exception {

        //On vérifie si le user est admin ou non, dépendamment de cette valeur, on ouvrira le fenêtre UserView ou AdminView

        ResultSet rs = Main.getConnexion().retrieveData(String.format("SELECT * FROM table_users WHERE username='%s' AND password='%s';",
                username_field.getText(), password_field.getText()));

        if(rs.next()) //C'est un utilisateur qui existe donc se connecte
        {
            if (rs.getBoolean("is_admin")) //C'est un adminitrateur, il verra un tableau de bord
            {
                User adminConnected = new User(username_field.getText(),password_field.getText());
                System.out.println("1  ADMIN : "+adminConnected);
                try {
                    loader = new FXMLLoader(getClass().getResource("../resources/AdminView.fxml"));
                    root = loader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Tableau de bord (ADMIN : "+ adminConnected.getUsername()+")");
                    stage.getIcons().add(new Image("/img/blood-sample.png"));
                    stage.setScene(new Scene(root, 958, 534));
                    stage.setResizable(false);
                    stage.show();
                    // Hide this current window
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else //Sinon c'est la fenêtre relative à un utilisateur !
            {
                    User connectedUser = new User(username_field.getText(),password_field.getText());
                try
                {
                    loader = new FXMLLoader(getClass().getResource("../resources/UserView.fxml"));
                    root = loader.load();
                    UserViewController userview_controller = loader.getController();
                    int nbe_notif = 0;
                    userview_controller.getNotif_button().setText(String.valueOf(nbe_notif));

                    rs = Main.getConnexion().retrieveData(String.format("SELECT * FROM table_users WHERE username='%s';",connectedUser.getUsername()));
                    userview_controller.setConnected_user_fromLogin(connectedUser.getUsername());
                    while(rs.next())
                    {
                        nbe_notif = rs.getInt("nbre_notifs");
                    }
                    userview_controller.getNotif_button().setText(String.valueOf(nbe_notif));
                    Stage stage = new Stage();
                    stage.setTitle("Ma page ("+connectedUser.getUsername()+")");
                    stage.getIcons().add(new Image("/img/sign-in.png"));
                    stage.setScene(new Scene(root, 791, 532));
                    stage.setResizable(false);
                    stage.show();
                    // Hide this current window
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else //L'utilisateur n'existe pas
        {
            error_text.setVisible(true);
        }
    }

    @FXML
    void signIn(ActionEvent event) throws IOException, SQLException {
        //Ici, on doit attribuer un username et un password + insertion des données
        /* D'abord, l'utilisateur choisit un username NON pris et insère son mdp, ensuite il pourra ajouter ses données de donneur*/

        //1. La fenêtre d'inscription user
        try {
            loader = new FXMLLoader(getClass().getResource("../resources/inscription_user.fxml"));
            root = loader.load();
            InscriptionUserController user_controller = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 484, 364));
            stage.getIcons().add(new Image("/img/sign-up.png"));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();

            // Ici on vérifie si le nom d'utilisateur est déjà pris!
            user_controller.getSignUp_button().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        ResultSet rs = Main.getConnexion().retrieveData(String.format("SELECT * FROM table_users WHERE username='%s'",
                                user_controller.getUsername_field_signup().getText()));

                        if(rs.next()) { //Le nom d'utilisateur est déjà pris
                            user_controller.getError_username().setVisible(true);
                        }
                        else{ // On crée un nouvel utilisateur dans la DB
                            user_controller.getError_username().setVisible(false);
                            if(user_controller.getPassword_field().getText().isEmpty()) user_controller.getEmptyPasswordError().setVisible(true);
                            else {
                                user_controller.getEmptyPasswordError().setVisible(false);
                                user_controller.setChosen_username(user_controller.getUsername_field_signup().getText());
                                user_controller.setChosen_password(user_controller.getPassword_field().getText());
                                user_controller.continuerInscription(actionEvent);
                            }
                        }
                    } catch (SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
            });

        //2. La fenêtre d'inscription donneur s'ouvrira dans le controller de Donneur

    }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void exit(ActionEvent event) {
        System.exit(1);
    }

    @FXML
    void reduceWindow(ActionEvent event) {
        ((Stage)((Button)event.getSource()).getScene().getWindow()).setIconified(true);

    }
}

