package Controlers;

import Tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CtrlPrescrire
{
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public CtrlPrescrire() {
        cnx = ConnexionBDD.getCnx();
    }

    public void InsertPrescrire(int idConsult, int numMedicament,int quantite) throws SQLException {
        ps = cnx.prepareStatement("INSERT INTO prescrire (prescrire.numConsult, prescrire.numMedoc, prescrire.quantite)\n" +
                "VALUES ("+idConsult+", "+numMedicament+", "+quantite+")");
        ps.executeUpdate();
    }
}
