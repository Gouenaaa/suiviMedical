package Vues;

import Tools.ConnexionBDD;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class FrmMenu extends JFrame{
    private JPanel pnlRoot;
    private JButton btnConsulter;
    private JButton btnPrescrire;
    private JLabel lblTitre;
    public FrmMenu() throws SQLException, ClassNotFoundException {
        this.setTitle("Suivi médical");
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        ConnexionBDD cnx = new ConnexionBDD();

        btnConsulter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrmConsulter frm = new FrmConsulter();
                frm.setVisible(true);
            }
        });
        btnPrescrire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrmPrescrire frm = new FrmPrescrire();
                frm.setVisible(true);
            }
        });
    }
}
