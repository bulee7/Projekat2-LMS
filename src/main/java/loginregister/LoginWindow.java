package loginregister;

import javax.swing.*;
import java.awt.*;
import mainmenu.MainMenuWindow;

public class LoginWindow extends JFrame {
    private final UserService userService;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginWindow() {
        super("Login");
        userService = new UserService();

        setSize(400, 200);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Unesite svoje podatke"));

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        formPanel.add(new JLabel("Korisnicko ime : "));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("Lozinka :"));
        formPanel.add(passwordField);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        loginButton.addActionListener(e -> login());
        registerButton.addActionListener(e -> openRegisterWindow());

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (userService.login(username, password)) {
            JOptionPane.showMessageDialog(this, "Uspješan login!", "Čestitke na prijavi!", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new MainMenuWindow(username);
        } else {
            JOptionPane.showMessageDialog(this, "Unijeli ste pogrešno korisničko ime ili lozinku!", "Neuspješan login!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openRegisterWindow() {
        new RegisterWindow();
    }
}


