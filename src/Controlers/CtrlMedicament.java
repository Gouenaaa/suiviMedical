package Controlers;

import Entities.Consultation;
import Entities.Medicament;
import Tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CtrlMedicament
{
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public CtrlMedicament() {
        cnx = ConnexionBDD.getCnx();
    }

    public ArrayList<Medicament> GetAllMedicamentsByIdConsultations(int idConsultation) throws SQLException {
        ArrayList<Medicament> lesMedicaments = new ArrayList<>();
        ps = cnx.prepareStatement("SELECT medicament.idMedoc, medicament.nomMedoc, medicament.prixMedoc\n" +
                "FROM medicament\n" +
                "JOIN prescrire ON medicament.idMedoc = prescrire.numMedoc\n" +
                "JOIN consultation ON prescrire.numConsult = "+idConsultation+"\n" +
                "GROUP BY medicament.idMedoc\n" +
                "ORDER BY medicament.idMedoc");
        rs = ps.executeQuery();
        while(rs.next()){
            Medicament medicament = new Medicament(rs.getInt("idMedoc"), rs.getString("nomMedoc"), rs.getDouble("prixMedoc"));
            lesMedicaments.add(medicament);
        }
        return lesMedicaments;
    }

    public ArrayList<Medicament> getAllMedicaments() throws SQLException {
        ArrayList<Medicament> lesMedicaments = new ArrayList<>();
        ps = cnx.prepareStatement("SELECT medicament.idMedoc, medicament.nomMedoc, medicament.prixMedoc\n" +
                "FROM medicament");
        rs = ps.executeQuery();
        while(rs.next()){
            Medicament medicament = new Medicament(rs.getInt("idMedoc"), rs.getString("nomMedoc"), rs.getDouble("prixMedoc"));
            lesMedicaments.add(medicament);
        }
        return lesMedicaments;
    }
}
