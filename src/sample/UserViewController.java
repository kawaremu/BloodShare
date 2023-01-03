package sample;

import classes.Message;
import classes.Notification;
import classes.PopulateApplicationData;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserViewController implements Initializable {
    private FXMLLoader loader;
    private Parent root;
    private String connected_user_fromLogin;
    ArrayList<String> notifs;

    @FXML
    private Button etat_button;

    @FXML
    private JFXButton notif_button;

    @FXML
    private Button logout_button;

    @FXML
    private Button preferences_button;

    @FXML
    private JFXButton ajouter_don_button;

    @FXML
    private JFXButton demander_sang_button;

    @FXML
    private JFXButton forum_button;


    @FXML
    private Tooltip tool_tip_notif;


    public JFXButton getNotif_button() {
        return notif_button;
    }

    public void setNotif_button(JFXButton notif_button) {
        this.notif_button = notif_button;
    }

    public UserViewController()
    {
        this.connected_user_fromLogin = "";
        this.notifs = new ArrayList<>();

    }

    @FXML
    void ajouterDon(ActionEvent event)
    {       //On ouvre la fenetre FaireDon
        try {
            loader = new FXMLLoader(getClass().getResource("../resources/faireDon.fxml"));
            root = loader.load();
            faireDonController faireDon_controller = loader.getController();
            faireDon_controller.setConnected_user_fromUserView(this.connected_user_fromLogin);
            Stage stage = new Stage();
            stage.getIcons().add(new Image("/img/donor.png"));
            stage.setTitle("Vous êtes généreux! ❤");
            stage.setScene(new Scene(root, 402, 333));
            stage.setResizable(false);
            stage.show();
            //((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void consulterForum(ActionEvent event) {
        try {
            loader = new FXMLLoader(getClass().getResource("../resources/forum.fxml"));
            root = loader.load();
            ForumController forum_controller = loader.getController();
            forum_controller.setConnected_user(this.connected_user_fromLogin);
            Stage stage = new Stage();
            stage.getIcons().add(new Image("/img/chat-group.png"));
            stage.setTitle("Forum des donneurs/receveurs ("+this.connected_user_fromLogin +")");
            stage.setScene(new Scene(root, 607, 567));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML

    void demanderSang(ActionEvent event) {
        //On ouvre la fenetre demanderSang
        try {
            loader = new FXMLLoader(getClass().getResource("../resources/demanderSang.fxml"));
            root = loader.load();
            demanderSangController demanderSang_controller = loader.getController();
            demanderSang_controller.setConnected_user_fromUserView(this.connected_user_fromLogin);
            Stage stage = new Stage();
            stage.getIcons().add(new Image("/img/syringe.png"));
            stage.setTitle("Vous avez besoin de sang ?");
            stage.setScene(new Scene(root, 402, 333));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void logout(ActionEvent event) {
        try {
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
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void voirEtatSante(ActionEvent event)
    {

    }

    @FXML
    void voirNotifs(ActionEvent event)
    {
        try
        {
            ArrayList<Notification> notifs_db = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String notif = "";

            //Remplissage de la table de notifications
            ResultSet rs = Main.getConnexion().retrieveData(String.format("SELECT * FROM infos_notif WHERE username='%s';",
                    this.connected_user_fromLogin));
            while(rs.next())
            {
                notifs_db.add(new Notification(rs.getTimestamp("arrivee_a").toLocalDateTime(),rs.getString("type_notif")));
            }

            System.out.println("Vos notifications non lues ("+notifs_db.size()+") :");
            for (Notification notification : notifs_db)
            {
                notif = new String(notification.getArrivee_a().format(formatter) + " " + notification.getType_notif());
                System.out.println(notif);
                notifs.add(notif);
            }

            StringBuilder notif_a_afficher = new StringBuilder(notifs.size()+1);
            for(int i=0; i<notifs.size() -1 ;i++)
            {
                notif_a_afficher.append(notifs.get(i)+"\n");
            }

            this.notif_button.setTooltip(tool_tip_notif);
            tool_tip_notif.setStyle("-fx-font-size: 13");
            tool_tip_notif.setPrefSize(250,300);
            tool_tip_notif.setText(notif_a_afficher.toString());
            tool_tip_notif.setWrapText(true);
            if(this.notif_button.isHover()) tool_tip_notif.setPrefSize(250,400);
            this.notif_button.setText("0");

            //UPDATE table_users SET nbre_notifs = 0 WHERE username='%s';
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    void voirPreferences(ActionEvent event) {

    }



    public void setConnected_user_fromLogin(String connected_user_fromLogin)
    {
        this.connected_user_fromLogin = connected_user_fromLogin;
    }

    public String getConnected_user_fromLogin()
    {
        return connected_user_fromLogin;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try
        {
            ArrayList<Notification> notifs_db = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String notif = "";

            //Remplissage de la table de notifications
            ResultSet rs = Main.getConnexion().retrieveData(String.format("SELECT * FROM infos_notif WHERE username='%s';",
                    this.connected_user_fromLogin));
            while(rs.next())
            {
                notifs_db.add(new Notification(rs.getTimestamp("arrivee_a").toLocalDateTime(),rs.getString("type_notif")));
            }

            for (Notification notification : notifs_db)
            {
                notif = new String(notification.getArrivee_a().format(formatter) + " " + notification.getType_notif());
                notifs.add(notif);
            }

            StringBuilder notif_a_afficher = new StringBuilder(notifs.size()+1);
            for(int i=0; i<notifs.size() -1 ;i++)
            {
                notif_a_afficher.append(notifs.get(i)+"\n");
            }

            this.notif_button.setTooltip(tool_tip_notif);
            tool_tip_notif.setStyle("-fx-font-size: 12");
            tool_tip_notif.setPrefSize(255,60);
            tool_tip_notif.setText("Cliquez sur ce bouton pour afficher vos notifications.");
            tool_tip_notif.setWrapText(true);


            //UPDATE table_users SET nbre_notifs = 0 WHERE username='%s';
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}