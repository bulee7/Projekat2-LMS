package mainmenu;

import javax.swing.*;
import  loginregister.LoginWindow;

public class MainMenuWindow {
    private JPanel JPanel;
    private JLabel mainMenu;
    private JButton modulesButton;
    private JButton financeAppButton;
    private JButton viewProfileButton;
    private JFrame frame;

    public MainMenuWindow(String username){
        frame = new JFrame("Glavni meni");
        frame.setContentPane(JPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        mainMenu.setText("Dobrodošao na glavni meni, " + username + "!");

        viewProfileButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame,"Ovdje će biti opcije za uređivanje profila", "Moj profil", JOptionPane.INFORMATION_MESSAGE);
        });

        financeAppButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Prikaz Finance aplikacije - projekat 1", "Finance App", JOptionPane.INFORMATION_MESSAGE);
        });

        modulesButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Otvaranje ostalih modula (trackera)", "Moduli(trackeri)", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}
