package loginregister;

import javax.swing.*;
import java.awt.*;

    public class RegisterWindow extends JFrame {
        private final UserService userService;
        private JTextField usernameField;
        private JPasswordField passwordField;

        public RegisterWindow() {
            super("Register");
            userService = new UserService();

            setSize(400, 200);
            setLayout(new BorderLayout(10, 10));
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
            initComponents();
            setVisible(true);
        }

        private void initComponents() {
            JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
            formPanel.setBorder(BorderFactory.createTitledBorder("Kreiraj novi profil"));

            usernameField = new JTextField(20);
            passwordField = new JPasswordField(20);

            formPanel.add(new JLabel("Korisničko ime : "));
            formPanel.add(usernameField);
            formPanel.add(new JLabel("Lozinka :"));
            formPanel.add(passwordField);

            add(formPanel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton registerButton = new JButton("Register");
            JButton cancelButton = new JButton("Cancel");

            registerButton.addActionListener(e -> register());
            cancelButton.addActionListener(e -> dispose());

            buttonPanel.add(registerButton);
            buttonPanel.add(cancelButton);
            add(buttonPanel, BorderLayout.SOUTH);
        }

        private void register() {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

           if (username.isEmpty() || password.isEmpty()) {
               JOptionPane.showMessageDialog(this, "Molimo Vas da popunite sva polja!", "Greška ulaza!", JOptionPane.ERROR_MESSAGE);
               return;
           }

           if (userService.register(username,password)) {
               JOptionPane.showMessageDialog(this, "Korisnik uspješno registrovan!", "Čestitke na registraciji!", JOptionPane.INFORMATION_MESSAGE);
               dispose();
           } else {
               JOptionPane.showMessageDialog(this, "Korisničko ime već postoji!", "Neuspješna registracija!", JOptionPane.ERROR_MESSAGE);
           }
        }
    }
