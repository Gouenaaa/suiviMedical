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

public class CtrlConsultation
{
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public CtrlConsultation() {
        cnx = ConnexionBDD.getCnx();
    }

    public ArrayList<Consultation> GetAllConsultations() throws SQLException {
        ArrayList<Consultation> lesConsultations = new ArrayList<>();
        ps = cnx.prepareStatement("SELECT consultation.idConsult, consultation.dateConsult, patient.nomPatient, medecin.nomMedecin, ROUND(SUM(prescrire.quantite*(medicament.prixMedoc-medicament.prixMedoc*vignette.tauxRemb/100)),2)\n" +
                "FROM consultation\n" +
                "JOIN patient ON consultation.numPatient = patient.idPatient\n" +
                "JOIN medecin ON consultation.numMedecin = medecin.idMedecin\n" +
                "JOIN prescrire ON consultation.idConsult = prescrire.numConsult\n" +
                "JOIN medicament ON prescrire.numMedoc = medicament.idMedoc\n" +
                "JOIN vignette ON medicament.numVignette = vignette.idVignette\n" +
                "GROUP BY consultation.idConsult\n" +
                "ORDER BY consultation.idConsult");
        rs = ps.executeQuery();
        while(rs.next()){
            Consultation consultation = new Consultation(rs.getInt("idConsult"), rs.getString("dateConsult"), rs.getString("nomPatient"), rs.getString("nomMedecin"), rs.getDouble(5));
            lesConsultations.add(consultation);
        }
        return lesConsultations;
    }
    public int getLastNumberOfConsultation() throws SQLException {
        int num = 0;
        ps = cnx.prepareStatement("SELECT MAX(consultation.idConsult)\n" +
                "FROM consultation");
        rs = ps.executeQuery();
        if(rs.next()){
            num = rs.getInt(1);
        }
        return num;
    }
    public void InsertConsultation(int idConsult, String dateConsultation, int numPatient,int numMedecin) throws SQLException {
        ps = cnx.prepareStatement("INSERT INTO consultation (consultation.idConsult, consultation.dateConsult, consultation.numPatient, consultation.numMedecin)\n" +
                "VALUES ("+idConsult+", '"+dateConsultation+"', "+numPatient+", "+numMedecin+")");
        ps.executeUpdate();
    }
}
