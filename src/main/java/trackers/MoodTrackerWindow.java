package trackers;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.HashMap;
import java.util.Map;

public class MoodTrackerWindow extends JFrame {

    private JTextField dateField;
    private JComboBox<String>moodCombo;
    private JTextArea descriptionArea;
    private JButton addButton;
    private JTable moodTable;
    private DefaultTableModel tableModel;
    private JButton statisticsButton;
    private MongoCollection<Document>moodCollection;

    public MoodTrackerWindow() {
        super("Tracker raspoloženja korisnika");

        MongoDatabase db = MongoDBConnection.getDatabase();
        moodCollection = db.getCollection("MoodTracker");

        setSize(700,600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel(new GridLayout(3,2,10,10));
        inputPanel.add(new JLabel("Unesite datum : "));
        dateField = new JTextField();
        inputPanel.add(dateField);

        inputPanel.add(new JLabel("Izaberite raspoloženje : "));
        moodCombo = new JComboBox<>(new String[]{"Sretan", "Tužan", "Ljut", "Umoran", "Motivisan", "Nervozan"});
        inputPanel.add(moodCombo);

        inputPanel.add(new JLabel("Opišite razlog Vašeg raspoloženja : "));
        descriptionArea = new JTextArea(3,30);
        inputPanel.add(new JScrollPane(descriptionArea));

        addButton = new JButton("Dodaj");

        tableModel = new DefaultTableModel(new Object[]{"Datum", "Raspoloženje", "Opis"}, 0);
        moodTable = new JTable(tableModel);
        moodTable.setFont(new Font("Arial", Font.BOLD, 14));
        moodTable.setRowHeight(20);
        moodTable.setBackground(new Color(255,255,20));

        for (Document document : moodCollection.find()) {
            tableModel.addRow(new Object[]{document.getString("datum"), document.getString("raspoloženje"), document.getString("opis")});
        }

        statisticsButton = new JButton("Prikaz statistike");

        setLayout(new BorderLayout(10,10));
        add(inputPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10,10));
        centerPanel.add(addButton, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(moodTable), BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        add(statisticsButton, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            String date = dateField.getText().trim();
            String mood = (String) moodCombo.getSelectedItem();
            String desc = descriptionArea.getText().trim();

            if (!date.isEmpty() && !mood.isEmpty()) {
                tableModel.addRow(new Object[]{date, mood, desc});
                dateField.setText("");
                descriptionArea.setText("");

                Document document = new Document("datum", date).append("raspoloženje", mood).append("opis", desc);
                moodCollection.insertOne(document);
            } else {
                JOptionPane.showMessageDialog(this, "Molimo Vas da unesete datum i izaberete raspoloženje!");
            }
        });

            statisticsButton.addActionListener(e -> {
                int redovi = tableModel.getRowCount();
                if (redovi == 0) {
                    JOptionPane.showMessageDialog(this, "Nema dostupnih podataka za statistiku!");
                    return;
                }
                Map<String, Integer> brojac = new HashMap<>();
                for (int i=0; i<redovi; i++) {
                    String mood = (String) tableModel.getValueAt(i,1);
                    brojac.put(mood, brojac.getOrDefault(mood,0) + 1);
                }

                JFrame statisticsFrame = new JFrame("Statistika raspoloženja korisnika");
                statisticsFrame.setSize(400,300);
                statisticsFrame.setLocationRelativeTo(this);

                JTextArea statisticsArea = new JTextArea();
                statisticsArea.setEditable(false);
                statisticsArea.append("Broj pojavljivanja raspoloženja : \n");
                brojac.forEach((m,c)-> statisticsArea.append(m + " : " + c + "\n"));

                statisticsFrame.add(new JScrollPane(statisticsArea), BorderLayout.CENTER);
                statisticsFrame.setVisible(true);
            });
            setVisible(true);
    }
}