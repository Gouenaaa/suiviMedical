package Controlers;

import Tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CtrlMedecin
{
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public CtrlMedecin() {
        cnx = ConnexionBDD.getCnx();
    }

    public ArrayList<String> getAllMedecins() throws SQLException {
        ArrayList<String> lesMedecins = new ArrayList<>();
        ps = cnx.prepareStatement("SELECT medecin.nomMedecin\n" +
                "FROM medecin");
        rs = ps.executeQuery();
        while(rs.next()){
            lesMedecins.add(rs.getString("nomMedecin"));
        }
        return lesMedecins;
    }

    public int getIdMedecinByName(String nomMed) throws SQLException {
        int id = 0;
        ps = cnx.prepareStatement("SELECT medecin.idMedecin\n" +
                "FROM medecin\n" +
                "WHERE medecin.nomMedecin = '"+nomMed+"'");
        rs = ps.executeQuery();
        if(rs.next()){
            id = rs.getInt("idMedecin");
        }
        return id;
    }
}
