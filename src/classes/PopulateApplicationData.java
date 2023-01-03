package classes;

import com.mysql.cj.protocol.Resultset;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class PopulateApplicationData {

    public static ObservableList<Don> stockSang;
    public static ObservableList<Donneur> repertoireDonneurs;
    public static ObservableList<DemandeSang> requetesReceveurs;
    public static ObservableList<Message> forum;

    public PopulateApplicationData() {
        this.stockSang = FXCollections.observableArrayList();
        this.repertoireDonneurs = FXCollections.observableArrayList();
        this.requetesReceveurs = FXCollections.observableArrayList();
        this.forum = FXCollections.observableArrayList();
        populateData();
    }

    public static void updateDataDons(){
        try
        {
            //Remplissage liste de dons
            ResultSet rs = Main.getConnexion().retrieveData("SELECT * FROM table_dons;");
            while (rs.next()) {
                stockSang.add(new Don(rs.getInt("id_don"), rs.getString("nom_complet"), rs.getString("groupe_sanguin"),
                        rs.getString("type_sang"), rs.getDate("date_collecte").toLocalDate(),
                        rs.getString("medecin_responsable"), rs.getString("hopital")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    public static void updateDataDonneurs() {
        try
        {
            //Remplissage répertoire des donneurs
            ResultSet rs = Main.getConnexion().retrieveData("SELECT * FROM table_donneurs;");

            while(rs.next()) {
                repertoireDonneurs.add(new Donneur(rs.getString("nom"),rs.getString("adresse"),rs.getString("email"),
                        rs.getInt("date_naissance"),rs.getString("genre"),rs.getString("groupe_sanguin"),
                        rs.getString("remarques"),rs.getInt("nombre_don")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void updateDataForum() {
        try {
            ResultSet rs = Main.getConnexion().retrieveData("SELECT * FROM table_forum;");
            while (rs.next())
            {
                forum.add(new Message(rs.getString("username"), rs.getString("contenu_message"),
                        rs.getTimestamp("envoye_a").toLocalDateTime()));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

        public void populateData(){
        try {
            //Remplissage liste de dons
            ResultSet rs = Main.getConnexion().retrieveData("SELECT * FROM table_dons;");
            while (rs.next())
            {
                stockSang.add(new Don(rs.getInt("id_don"),rs.getString("nom_complet"),rs.getString("groupe_sanguin"),
                    rs.getString("type_sang"),rs.getDate("date_collecte").toLocalDate(),
                        rs.getString("medecin_responsable"),rs.getString("hopital")));

            }

            //Remplissage répertoire des donneurs
            rs = Main.getConnexion().retrieveData("SELECT * FROM table_donneurs;");
            while(rs.next()){
                repertoireDonneurs.add(new Donneur(rs.getString("nom"),rs.getString("adresse"),rs.getString("email"),LocalDate.now().getYear()-
                        rs.getInt("date_naissance"),rs.getString("genre"),rs.getString("groupe_sanguin"),
                        rs.getString("remarques"),rs.getInt("nombre_don")));
            }

          //Remplissage de la table des demandes
            rs = Main.getConnexion().retrieveData("SELECT * FROM table_demandes;");
            while(rs.next())
            {
                requetesReceveurs.add(new DemandeSang(rs.getTimestamp("cree_a").toLocalDateTime(),rs.getDate("date_demande").toLocalDate(),
                        rs.getString("demande_par"),rs.getString("groupe_sanguin"),rs.getString("objectifs"),rs.getString("type_sang")));
            }

            //Remplissage de la table_forum
            rs = Main.getConnexion().retrieveData("SELECT * FROM table_forum;");
            while(rs.next())
            {
                forum.add(new Message(rs.getString("username"),rs.getString("contenu_message"),
                        rs.getTimestamp("envoye_a").toLocalDateTime()));
            }


        }catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            System.out.println("Données récupérées avec succès.");
        }
    }

    public ObservableList<Don> getStockSang() {
        return stockSang;
    }


}
