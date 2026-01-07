package trackers;

import javax.swing.*;
import java.awt.*;

public class ModulesWindow extends JFrame {

    public ModulesWindow() {
        super("Moduli/Trackeri");
        setSize(550,400);
        setDefaultCloseOperation((JFrame.DISPOSE_ON_CLOSE));
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4,1,10,10));

        JButton modul1 = new JButton("Sleep tracker");
        JButton modul2 = new JButton("Mood tracker");
        JButton modul3 = new JButton("Fitness tracker");
        JButton modul4 = new JButton("Movie tracker");

        modul1.addActionListener(e -> new SleepTrackerWindow());
        modul2.addActionListener(e -> new MoodTrackerWindow());
        modul3.addActionListener(e -> new FitnessTrackerWindow());
        modul4.addActionListener(e -> new MovieTrackerWindow());

        add(modul1);
        add(modul2);
        add(modul3);
        add(modul4);

        setVisible(true);
    }
}
