package mainmenu;

import javax.swing.*;
import loginregister.ViewProfileWindow;

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
            new ViewProfileWindow().setVisible(true);
        });

        financeAppButton.addActionListener(e -> {
            new financeapp.FinanceAppWindow();
        });

        modulesButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Otvaranje ostalih modula (trackera)", "Moduli(trackeri)", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}
