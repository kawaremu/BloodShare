package sample;

import classes.Message;
import classes.PopulateApplicationData;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ForumController implements Initializable {
    String connected_user;
    ArrayList<String> messages;

    public void setConnected_user(String connected_user) {
        this.connected_user = connected_user;
    }

    public ForumController() {
        this.messages = new ArrayList<String>();
    }

    @FXML
    private TextArea message_area;

    @FXML
    private Button envoyer_button;

    @FXML
    private ListView<String> forum_messages;

    @FXML
    private Label erreur;

    @FXML
    void newMessage(ActionEvent event) {
        if (this.message_area.getText().isEmpty()) erreur.setVisible(true);
        else {
            erreur.setVisible(false);
            try {
                PreparedStatement prepstmt = Main.getConnexion().getConnection().
                        prepareStatement("INSERT INTO table_forum(username,contenu_message) VALUES(?,?)");
                int nbre_insertion = prepstmt.executeUpdate(String.format("INSERT INTO table_forum(username,contenu_message) VALUES ('%s','%s')",
                        this.connected_user, this.message_area.getText()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String message = new String(this.connected_user + " : " + this.message_area.getText() + " (à " + LocalDateTime.now().format(formatter) + ")");
            messages.add(message);
            this.message_area.clear();
            this.forum_messages.setItems(FXCollections.observableArrayList(messages));
            PopulateApplicationData.updateDataForum();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String message = "";
        for (Message m : PopulateApplicationData.forum) {
            message = new String(m.getEnvoye_par() + " : " + m.getContenu_message() + " (à " + m.getEnvoye_a().format(formatter) + ")");
            messages.add(message);
        }
        this.forum_messages.setItems(FXCollections.observableArrayList(messages));
    }
}