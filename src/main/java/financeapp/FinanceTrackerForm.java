package financeapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class FinanceTrackerForm {
    private JPanel mainPanel;
    private JTextField amountField;
    private JComboBox<String> descriptionCombo;
    private JComboBox<String> typeCombo;
    private JButton addButton;
    private JTable transactionTable;
    private JLabel incomeLabel;
    private JLabel expenseLabel;
    private JLabel balanceLabel;

    private TransactionManager manager;

    public FinanceTrackerForm() {
       mainPanel = new JPanel(new BorderLayout());

       JLabel titleLabel = new JLabel("Finance App - Upravljanje ličnim finansijama");
       titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
       titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
       mainPanel.add(titleLabel, BorderLayout.NORTH);

       amountField = new JTextField(20);
       String[] opisi = {"Plata", "Stan", "Hrana", "Prijevoz", "Zabava", "Ostalo"};
       descriptionCombo = new JComboBox<>(opisi);
       typeCombo = new JComboBox<>(new String[]{"Prihod", "Rashod"});
       addButton = new JButton("Dodaj transakciju");

       JPanel inputPanel = new JPanel();
       inputPanel.setLayout(new BoxLayout(inputPanel,BoxLayout.Y_AXIS));
       inputPanel.setBorder(BorderFactory.createTitledBorder("Unos transakcije"));

       inputPanel.add(new JLabel("Vrsta:"));
       inputPanel.add(typeCombo);
       inputPanel.add(new JLabel("Iznos:"));
       inputPanel.add(amountField);
       inputPanel.add(new JLabel("Opis/kategorija:"));
       inputPanel.add(descriptionCombo);

       JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
       buttonPanel.add(addButton);
       inputPanel.add(buttonPanel);

        incomeLabel = new JLabel("Prihod : 0");
        expenseLabel = new JLabel("Rashod : 0");
        balanceLabel = new JLabel("Balans : 0");

        incomeLabel.setForeground(Color.GREEN.darker());
        expenseLabel.setForeground(Color.RED);
        balanceLabel.setForeground(Color.BLUE);

        JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Pregled"));
        summaryPanel.add(incomeLabel);
        summaryPanel.add(expenseLabel);
        summaryPanel.add(balanceLabel);

        transactionTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista transakcija"));

        JPanel layoutPanel = new JPanel();
        layoutPanel.setLayout(new BoxLayout(layoutPanel, BoxLayout.Y_AXIS));
        layoutPanel.add(inputPanel);
        layoutPanel.add(summaryPanel);
        layoutPanel.add(scrollPane);

        mainPanel.add(layoutPanel, BorderLayout.CENTER);

        manager = new TransactionManager();
        loadDataIntoTable();
        updateSummary();

        addButton.addActionListener(e -> {
            try {
                String type = (String) typeCombo.getSelectedItem();
                double amount = Double.parseDouble(amountField.getText());
                String description = (String) descriptionCombo.getSelectedItem();

                if (description.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Description cannot be empty!");
                    return;
                }

                Transaction t = new Transaction(type, amount, description);
                manager.addTransaction(t);
                loadDataIntoTable();
                updateSummary();

                amountField.setText(" ");
                descriptionCombo.setSelectedIndex(0);
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(null, "Amount must be a number!");
            }
        });
    }

        private void loadDataIntoTable() {
        ArrayList<Transaction>list = manager.getAllTransactions();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Vrsta");
        model.addColumn("Iznos");
        model.addColumn("Opis/kategorija");

        for (Transaction t : list) {
            model.addRow(new Object[] {
                t.getType(),
                t.getAmount(),
                t.getDescription()
            });
        }
        transactionTable.setModel(model);
        }

        private void updateSummary() {
        double income = manager.getTotalIncome();
        double expense = manager.getTotalExpense();
        double balance = income - expense;

        incomeLabel.setText("Prihod : " +income);
        expenseLabel.setText("Rashod : " +expense);
        balanceLabel.setText("Balans : " +balance);
        }

        public  JPanel getMainPanel() {
        return mainPanel;
        }
}


