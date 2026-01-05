package loginregister;

import javax.swing.*;
import java.awt.*;

public class ViewProfileWindow extends JFrame {

    private final UserService userService;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public ViewProfileWindow() {
        userService = new UserService();
        setTitle("Uredi profil korisnika");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Korisničko ime : "), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(20);
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Nova lozinka : "), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

        JButton buttonEditUsername = new JButton("Uredi korisničko ime");
        JButton buttonEditPassword = new JButton("Uredi lozinku");
        JButton buttonDeleteUser = new JButton("Izbriši korisnika");
        JButton buttonShowUsers = new JButton("Prikaži sve korisnike");

        buttonPanel.add(buttonEditUsername);
        buttonPanel.add(buttonEditPassword);
        buttonPanel.add(buttonDeleteUser);
        buttonPanel.add(buttonShowUsers);

        panel.add(buttonPanel, gbc);
        add(panel, BorderLayout.CENTER);

        buttonEditUsername.addActionListener(e -> editUsername());
        buttonEditPassword.addActionListener(e -> editPassword());
        buttonDeleteUser.addActionListener(e -> deleteUser());
        buttonShowUsers.addActionListener(e -> showAllUsers());

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
    }

    private void editUsername() {
        String oldUsername = usernameField.getText().trim();
        String newUsername = JOptionPane.showInputDialog(this, "Unesi novo korisničko ime : ");
        if (newUsername != null && !newUsername.isEmpty()) {
            if (userService.postojiKorisnik(oldUsername)) {
                int izbor = JOptionPane.showConfirmDialog(this, "Želite li promijeniti korisničko ime sa : " + oldUsername + " na " + newUsername + "?", "Potvrda ažuriranja!", JOptionPane.YES_NO_OPTION);
                if (izbor == JOptionPane.YES_OPTION) {
                    userService.azurirajKorisnickoIme(oldUsername, newUsername);
                    JOptionPane.showMessageDialog(this, "Korisničko ime promijenjeno sa : " + oldUsername + " na " + newUsername);
                } else {
                    JOptionPane.showMessageDialog(this, "Korisnik" + oldUsername + "ne postoji!");
                }
            }
        }
    }

    private void editPassword() {
        String username = usernameField.getText().trim();
        String newPassword = new String(passwordField.getPassword()).trim();
        if (!username.isEmpty() && !newPassword.isEmpty()) {
            if (userService.postojiKorisnik(username)) {
                int izbor = JOptionPane.showConfirmDialog(this, "Želite li promijeniti lozinku za korisnika : " + username + "?", "Potvrda promjene lozinke!", JOptionPane.YES_NO_OPTION);
                if (izbor == JOptionPane.YES_OPTION) {
                    userService.azurirajLozinku(username, newPassword);
                    JOptionPane.showMessageDialog(this, "Lozinka za korisnika " + username + " uspješno promijenjena!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Korisnik" + username + " ne postoji!");
            }
        }
    }

    private void deleteUser() {
        String username = usernameField.getText().trim();
        if (!username.isEmpty()) {
            if (userService.postojiKorisnik(username)) {
                int izbor = JOptionPane.showConfirmDialog(this, "Želite li izbrisati korisnika : " + username + "?", "Potvrda brisanja!", JOptionPane.YES_NO_OPTION);
                if (izbor == JOptionPane.YES_OPTION) {
                    userService.izbrisiKorisnika(username);
                    JOptionPane.showMessageDialog(this, "Korisnik uspješno obrisan!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Korisnik" + username + " ne postoji!");
            }
        }
    }

    private void showAllUsers(){
        java.util.List<User>korisnici = userService.sviKorisnici();

        String[] kolone = {"ID", "Korisničko ime", "Hash lozinka"};
        Object[][] podaci = new Object[korisnici.size()][3];

        for (int i=0; i<korisnici.size(); i++) {
            User u = korisnici.get(i);
            podaci[i][0] = u.getId();
            podaci[i][1] = u.getKorisnickoIme();
            podaci[i][2] = u.getHashLozinka();
        }
        JTable tabela = new JTable(podaci,kolone);
        JScrollPane scrollPane = new JScrollPane(tabela);

        JDialog dialog = new JDialog(this, "Svi korisnici", true);
        dialog.setSize(500,300);
        dialog.setLocationRelativeTo(this);
        dialog.add(scrollPane);
        dialog.setVisible(true);
    }
}
