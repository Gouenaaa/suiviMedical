package Tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.TimeZone;

public class ConnexionBDD
{
    private static Connection cnx;

    public ConnexionBDD() throws ClassNotFoundException, SQLException {
        String server = "localhost:3306";
        String db = "suivimedical";
        String user = "root";
        String password = "turbo";
        String pilote = "com.mysql.jdbc.Driver";
        // chargement du pilote
        Class.forName(pilote);
        // L'objet connexion à la BDD avec le nom de la base, le user et le password
        cnx = DriverManager.getConnection("jdbc:mysql://"+server+"/"+db+"?serverTimezone="
                + TimeZone.getDefault().getID(), user, password);
    }
    public static Connection getCnx() {
        return cnx;
    }
}
