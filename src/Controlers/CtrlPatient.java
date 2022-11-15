package Controlers;

import Entities.Consultation;
import Tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CtrlPatient
{
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public CtrlPatient() {
        cnx = ConnexionBDD.getCnx();
    }

    public ArrayList<String> getAllPatients() throws SQLException {
        ArrayList<String> lesPatients = new ArrayList<>();
        ps = cnx.prepareStatement("SELECT patient.nomPatient\n" +
                "FROM patient");
        rs = ps.executeQuery();
        while(rs.next()){
            lesPatients.add(rs.getString("nomPatient"));
        }
        return lesPatients;
    }
    public int getIdPatientByName(String nomPat) throws SQLException {
        int id = 0;
        ps = cnx.prepareStatement("SELECT patient.idPatient\n" +
                "FROM patient\n" +
                "WHERE patient.nomPatient = '"+nomPat+"'");
        rs = ps.executeQuery();
        if(rs.next()){
            id = rs.getInt("idPatient");
        }
        return id;
    }
}
