package trackers;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.HashMap;
import java.util.Map;

public class FitnessTrackerWindow extends JFrame {

    private JTextField dateField;
    private JComboBox<String> activityCombo;
    private JTextArea descriptionArea;
    private JCheckBox completedCheckBox;
    private JButton addButton;
    private JTable fitnessTable;
    private DefaultTableModel tableModel;
    private JButton statisticsButton;
    private MongoCollection<Document>fitnessCollection;

    public FitnessTrackerWindow() {
        super("Tracker fizičkih aktivnosti korisnika");

        MongoDatabase db = MongoDBConnection.getDatabase();
        fitnessCollection = db.getCollection("FitnessTracker");

        setSize(700,600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel(new GridLayout(4,2,10,10));

        inputPanel.add(new JLabel("Unesite datum : "));
        dateField = new JTextField();
        inputPanel.add(dateField);

        inputPanel.add(new JLabel("Izaberite aktivnost : "));
        activityCombo = new JComboBox<>(new String[]{"Teretana", "Cardio", "Yoga/istezanje"});
        inputPanel.add(activityCombo);

        inputPanel.add(new JLabel("Opis aktivnosti : "));
        descriptionArea = new JTextArea(3,30);
        inputPanel.add(new JScrollPane(descriptionArea));

        inputPanel.add(new JLabel("Aktivnost završena : "));
        completedCheckBox = new JCheckBox();
        inputPanel.add(completedCheckBox);

        addButton = new JButton("Dodaj");

        tableModel = new DefaultTableModel(new Object[]{"Datum", "Aktivnost", "Opis", "Završeno"}, 0);
        fitnessTable = new JTable(tableModel);
        fitnessTable.setFont(new Font("Arial", Font.BOLD, 14));
        fitnessTable.setRowHeight(20);
        fitnessTable.setBackground(new Color(255,255,20));

        for (Document document : fitnessCollection.find()) {
            tableModel.addRow(new Object[]{document.getString("datum"), document.getString("aktivnost"), document.getString("opis"), document.getBoolean("završeno", false)});
        }

        statisticsButton = new JButton("Prikaz statistike");

        setLayout(new BorderLayout(10,10));
        add(inputPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10,10));
        centerPanel.add(addButton, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(fitnessTable), BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        add(statisticsButton, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            String date = dateField.getText().trim();
            String activity = (String) activityCombo.getSelectedItem();
            String desc = descriptionArea.getText().trim();
            boolean completed = completedCheckBox.isSelected();

            if (!date.isEmpty() && !activity.isEmpty()) {
                tableModel.addRow(new Object[]{date, activity, desc, completed});
                dateField.setText("");
                descriptionArea.setText("");
                completedCheckBox.setSelected(false);

                Document document = new Document("datum", date).append("aktivnost", activity).append("opis", desc).append("završeno", completed);
                fitnessCollection.insertOne(document);
            } else {
                JOptionPane.showMessageDialog(this, "Molimo Vas da unesete datum i izaberete aktivnost!");
            }
        });

            statisticsButton.addActionListener(e -> {
                int redovi = tableModel.getRowCount();
                if (redovi == 0) {
                    JOptionPane.showMessageDialog(this, "Nema dostupnih podataka za statistiku!");
                    return;
                }

                int brojacZavrsenih = 0;
                int brojacNezavrsenih = 0;
                Map<String,Integer>brojacAktivnosti = new HashMap<>();

                for (int i=0; i<redovi; i++) {
                    boolean zavrseno = (Boolean) tableModel.getValueAt(i,3);
                    if (zavrseno) brojacZavrsenih++;
                    else brojacNezavrsenih++;

                    String aktivnost = (String) tableModel.getValueAt(i,1);
                    brojacAktivnosti.put(aktivnost, brojacAktivnosti.getOrDefault(aktivnost,0) + 1);
                }

                JFrame statisticsFrame = new JFrame("Statistika aktivnosti korisnika");
                statisticsFrame.setSize(400,300);
                statisticsFrame.setLocationRelativeTo(this);

                JTextArea statisticsArea = new JTextArea();
                statisticsArea.setEditable(false);
                statisticsArea.append("Ukupan broj aktivnosti korisnika : " + redovi + "\n");
                statisticsArea.append("Završene aktivnosti korisnika : " + brojacZavrsenih + "\n");
                statisticsArea.append("Nezavršene aktivnosti korisnika : " + brojacNezavrsenih + "\n");

                statisticsFrame.add(new JScrollPane(statisticsArea), BorderLayout.CENTER);
                statisticsFrame.setVisible(true);
            });
            setVisible(true);
    }
}
