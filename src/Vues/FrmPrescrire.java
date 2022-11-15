package Vues;

import Controlers.*;
import Tools.ModelJTable;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FrmPrescrire extends JFrame
{
    private JPanel pnlRoot;
    private JLabel lblTitre;
    private JLabel lblNumero;
    private JLabel lblDate;
    private JLabel lblNomMedecin;
    private JTextField txtNumeroConsultation;
    private JComboBox cboPatients;
    private JComboBox cboMedecins;
    private JButton btnInserer;
    private JTable tblMedicaments;
    private JPanel pnlDate;
    private JLabel lblNomPatient;
    private JLabel lblMedicaments;
    private JDateChooser dcDateConsultation;
    private CtrlConsultation ctrlConsultation;
    private CtrlPatient ctrlPatient;
    private CtrlMedecin ctrlMedecin;
    private CtrlMedicament ctrlMedicament;
    private CtrlPrescrire ctrlPrescrire;
    private ModelJTable mdl;

    private void refreshFrm(){
    try {
        txtNumeroConsultation.setText(String.valueOf(ctrlConsultation.getLastNumberOfConsultation()+1));
    } catch (SQLException ex) {
        throw new RuntimeException(ex);
    }
    cboPatients.setSelectedIndex(0);
    cboMedecins.setSelectedIndex(0);
    try {
        mdl.loadDataMedicamentsConsultation(ctrlMedicament.getAllMedicaments());
    } catch (SQLException ex) {
        throw new RuntimeException(ex);
    }
    tblMedicaments.setModel(mdl);
    }


    public FrmPrescrire()
    {
        this.setTitle("Prescrire");
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        ctrlConsultation = new CtrlConsultation();
        ctrlPatient = new CtrlPatient();
        ctrlMedecin = new CtrlMedecin();
        ctrlMedicament = new CtrlMedicament();
        ctrlPrescrire = new CtrlPrescrire();
        mdl = new ModelJTable();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                dcDateConsultation = new JDateChooser();
                dcDateConsultation.setDateFormatString("yyyy-MM-dd");
                pnlDate.add(dcDateConsultation);

                try {
                    for(String p: ctrlPatient.getAllPatients()){
                        cboPatients.addItem(p);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    for(String m: ctrlMedecin.getAllMedecins()){
                        cboMedecins.addItem(m);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                refreshFrm();
            }
        });
        btnInserer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(dcDateConsultation.getDate() == null){
                    JOptionPane.showMessageDialog(null, "Veuillez choisir une date", "Erreur date", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    int idConsult = Integer.parseInt(txtNumeroConsultation.getText());
                    String date = String.valueOf(dcDateConsultation.getDate().getYear())+"-"+String.valueOf(dcDateConsultation.getDate().getMonth())+"-"+dcDateConsultation.getDate().getDay();
                    int idPatient;
                    int idMedecin;
                    try {
                        idPatient = ctrlPatient.getIdPatientByName(cboPatients.getSelectedItem().toString());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    try {
                        idMedecin = ctrlMedecin.getIdMedecinByName(cboMedecins.getSelectedItem().toString());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    try {
                        ctrlConsultation.InsertConsultation(idConsult, date, idPatient, idMedecin);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    int i = 0;
                    while(i < tblMedicaments.getRowCount()){
                        if(Integer.parseInt(tblMedicaments.getValueAt(i, 3).toString()) != 0){
                            int idMedoc = Integer.parseInt(tblMedicaments.getValueAt(i, 0).toString());
                            int quantite = Integer.parseInt(tblMedicaments.getValueAt(i, 3).toString());
                            try {
                                ctrlPrescrire.InsertPrescrire(idConsult, idMedoc, quantite);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                        i++;
                    }
                    JOptionPane.showMessageDialog(null, "La consulation est enregistrée", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                    refreshFrm();
                }
            }
        });
    }
}
