package trackers;

import javax.swing.*;
import java.awt.*;

public class ModulesWindow extends JFrame {

    public ModulesWindow() {
        super("Moduli/Trackeri");
        setSize(550,400);
        setDefaultCloseOperation((JFrame.DISPOSE_ON_CLOSE));
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2,2,10,10));

        JButton modul1 = new JButton("Modul/Tracker 1");
        JButton modul2 = new JButton("Modul/Tracker 2");
        JButton modul3 = new JButton("Modul/Tracker 3");
        JButton modul4 = new JButton("Modul/Tracker 4");

        modul1.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Ova opcija će biti dodana u kasnijoj verziji projekta!"));
        modul2.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Ova opcija će biti dodana u kasnijoj verziji projekta!"));
        modul3.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Ova opcija će biti dodana u kasnijoj verziji projekta!"));
        modul4.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Ova opcija će biti dodana u kasnijoj verziji projekta!"));

        add(modul1);
        add(modul2);
        add(modul3);
        add(modul4);

        setVisible(true);
    }
}
