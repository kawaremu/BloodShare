package classes;
import java.sql.*;

import javax.swing.JOptionPane;
public class Connexion {

        private String url, password;
        private Connection connection;
        private Statement statement;
        private ResultSet resultSet;

        public Connexion() {
            try
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
                this.url = "jdbc:mysql://localhost:3306/banque_sang"
                        + "?useLegacyDatetimeCode=false&serverTimezone=UTC";
                this.password = "123";
                this.connection = DriverManager.getConnection(url, "hopital", password);
                this.statement = connection.createStatement();

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "La connexion à la base de données a échoué.", "Erreur de connexion",
                        JOptionPane.ERROR_MESSAGE);
            }
            finally {
                //JOptionPane.showMessageDialog(null, "La connexion à la base de données a réussi.");
                //System.out.println("OK T CONNECTE FRR");
            }
        }

        public ResultSet retrieveData(String query) {
            try {
                this.resultSet = this.connection.createStatement().executeQuery(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return resultSet;
        }



        public void setResultSet(ResultSet resultSet) {
            this.resultSet = resultSet;
        }

        public Connection getConnection() {
            return connection;
        }


        public void setConnection(Connection connection) {
            this.connection = connection;
        }

        public Statement getStatement() {
            return statement;
        }

        public ResultSet getResultSet() {
            return resultSet;
        }


}




