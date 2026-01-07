package trackers;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class SleepTrackerWindow extends JFrame {

    private JTextField dateField;
    private JComboBox<Integer>hoursCombo;
    private JButton addButton;
    private JTable sleepTable;
    private DefaultTableModel tableModel;
    private JButton statisticsButton;
    private MongoCollection<Document>sleepCollection;

    public SleepTrackerWindow() {
        super("Tracker spavanja korisnika");

        MongoDatabase db = MongoDBConnection.getDatabase();
        sleepCollection = db.getCollection("SleepTracker");

        for (Document d : sleepCollection.find()) {
            tableModel.addRow(new Object[]{d.getString("datum"), d.getInteger("sati")});
        }

        setSize(600,500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel(new GridLayout(2,2,10,10));

        inputPanel.add(new JLabel("Unesite datum : "));
        dateField = new JTextField();
        inputPanel.add(dateField);

        inputPanel.add(new JLabel("Unesite broj sati spavanja : "));
        hoursCombo = new JComboBox<>();
        for (int i=0; i<=24; i++) {
            hoursCombo.addItem(i);
        }
        inputPanel.add(hoursCombo);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addButton = new JButton("Dodaj");
        buttonPanel.add(addButton);

        tableModel = new DefaultTableModel(new Object[]{"Datum", "Broj sati spavanja"}, 0);
        sleepTable = new JTable(tableModel);
        sleepTable.setFont(new Font("SansSerif", Font.BOLD, 14));
        sleepTable.setRowHeight(25);
        sleepTable.setBackground(new Color(255,255,20));

        statisticsButton = new JButton("Prikaz statistike");

        setLayout(new BorderLayout(10,10));
        add(inputPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10,10));
        centerPanel.add(buttonPanel, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(sleepTable),BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);
        add(statisticsButton, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            String date = dateField.getText().trim();
            Integer hours = (Integer) hoursCombo.getSelectedItem();
            if (!date.isEmpty()) {
                tableModel.addRow(new Object[]{date, hours});
                dateField.setText("");

                Document document = new Document("datum", date).append("sati", hours);
                sleepCollection.insertOne(document);

            } else {
                JOptionPane.showMessageDialog(this, "Molimo Vas da unesete datum!");
            }
        });

        statisticsButton.addActionListener(e -> {
            int brojRedova = tableModel.getRowCount();
            if (brojRedova == 0) {
                JOptionPane.showMessageDialog(this, "Nema dostupnih podataka za kreiranje statistike!");
                return;
            }
            double suma = 0;
            for (int i = 0; i< brojRedova; i++) {
                suma = suma + (Integer) tableModel.getValueAt(i,1);
            }
            double prosjek = suma/ brojRedova;

            JFrame statisticsFrame = new JFrame("Statistika spavanja korisnika");
            statisticsFrame.setSize(400,300);
            statisticsFrame.setLocationRelativeTo(this);

            JTable statisticsTable = new JTable(tableModel);
            statisticsFrame.add(new JScrollPane(statisticsTable), BorderLayout.CENTER);
            statisticsFrame.add(new JLabel("Prosjek sati spavanja korisnika : " + prosjek, SwingConstants.CENTER), BorderLayout.SOUTH);
            statisticsFrame.setVisible(true);
        });
        setVisible(true);
    }
}
