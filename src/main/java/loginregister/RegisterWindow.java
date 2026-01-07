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
            JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
            formPanel.setBorder(BorderFactory.createTitledBorder("Kreiraj novi profil"));

            usernameField = new JTextField(20);
            passwordField = new JPasswordField(20);

            formPanel.add(new JLabel("Korisničko ime : "));
            formPanel.add(usernameField);
            formPanel.add(new JLabel("Lozinka :"));
            formPanel.add(passwordField);

            String[] theme = {"Plava", "Zelena", "Roza", "Narandžasta", "Dark", "Cyberpunk"};
            JComboBox<String> themeCombo = new JComboBox<>(theme);

            formPanel.add(new JLabel("Izbor boje : "));
            formPanel.add(themeCombo);

            themeCombo.addActionListener(e -> {
                String color = (String) themeCombo.getSelectedItem();

                if (color.equals("Dark")) {
                    formPanel.setBackground(Color.DARK_GRAY);
                    for (Component c : formPanel.getComponents()) {
                        c.setForeground(Color.BLACK);
                    }
                } else if (color.equals("Cyberpunk")) {
                    formPanel.setBackground(new Color(20, 20, 40));
                    for (Component c : formPanel.getComponents()) {
                        c.setForeground(Color.MAGENTA);
                    }
                } else if (color.equals("Plava")) {
                    formPanel.setBackground(Color.BLUE);
                    for (Component c : formPanel.getComponents()) {
                        c.setForeground(Color.BLACK);
                    }
                } else if (color.equals("Zelena")) {
                    formPanel.setBackground(Color.GREEN);
                    for (Component c : formPanel.getComponents()) {
                        c.setForeground(Color.BLACK);
                    }
                } else if (color.equals("Roza")) {
                    formPanel.setBackground(Color.PINK);
                    for (Component c : formPanel.getComponents()) {
                        c.setForeground(Color.BLACK);
                    }
                } else if (color.equals("Narandžasta")) {
                    formPanel.setBackground(Color.ORANGE);
                    for (Component c : formPanel.getComponents()) {
                        c.setForeground(Color.BLACK);
                    }
                }
                formPanel.repaint();
            });

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
