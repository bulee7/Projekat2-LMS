package trackers;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class MovieTrackerWindow extends JFrame {

    private JTextField nameField;
    private JComboBox<String>genreCombo;
    private JComboBox<Integer>ratingCombo;
    private JButton addButton;
    private JTable movieTable;
    private DefaultTableModel tableModel;
    private JButton statisticsButton;
    private MongoCollection<Document>movieCollection;

    public MovieTrackerWindow() {
        super("Tracker filmova korisnika");

        MongoDatabase db = MongoDBConnection.getDatabase();
        movieCollection = db.getCollection("MovieTracker");

        setSize(700,600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel(new GridLayout(3,2,10,10));

        inputPanel.add(new JLabel("Unesite naziv filma : "));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Izaberite žanr filma : "));
        genreCombo = new JComboBox<>(new String[]{"Akcija", "Komedija", "Horor", "Avantura", "Ratni", "Animirani", "Sportski", "Sci-fi", "Romantika", "Drama", "Western", "Biografski", "Dokumentarni", "Kriminalistički"});
        inputPanel.add(genreCombo);

        inputPanel.add(new JLabel("Izaberite ocjenu filma : "));
        ratingCombo = new JComboBox<>();
        for (int i=1; i<=5; i++) {
            ratingCombo.addItem(i);
        }
        inputPanel.add(ratingCombo);

        addButton = new JButton("Dodaj");

        tableModel = new DefaultTableModel(new Object[]{"Naziv filma", "Žanr", "Ocjena"}, 0);
        movieTable = new JTable(tableModel);
        movieTable.setFont(new Font("Arial", Font.BOLD, 14));
        movieTable.setRowHeight(20);
        movieTable.setBackground(new Color(255,255,20));

        for (Document document : movieCollection.find()) {
            tableModel.addRow(new Object[]{document.getString("naziv"), document.getString("žanr"), document.getInteger("ocjena", 0)});
        }

        statisticsButton = new JButton("Prikaz statistike");

        setLayout(new BorderLayout(10,10));
        add(inputPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10,10));
        centerPanel.add(addButton, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(movieTable), BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        add(statisticsButton, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String genre = (String) genreCombo.getSelectedItem();
            Integer rating = (Integer) ratingCombo.getSelectedItem();

            if (!name.isEmpty()) {
                tableModel.addRow(new Object[]{name, genre, rating});
                nameField.setText("");

                Document document = new Document("naziv", name).append("žanr", genre).append("ocjena", rating);
                movieCollection.insertOne(document);
            } else {
                JOptionPane.showMessageDialog(this, "Molimo Vas da unesete naziv filma!");
            }
        });

        statisticsButton.addActionListener(e -> {
            int redovi = tableModel.getRowCount();
            if (redovi == 0) {
                JOptionPane.showMessageDialog(this, "Nema unesenih podataka za statistiku");
                return;
            }

            double suma = 0;
            for (int i=0; i<redovi; i++) {
                suma = suma + (Integer) tableModel.getValueAt(i,2);
            }
            double prosjek = suma/redovi;

            JFrame statisticsFrame = new JFrame("Statistika pogledanih filmova korisnika");
            statisticsFrame.setSize(550,450);
            statisticsFrame.setLocationRelativeTo(this);

            JTable statisticsTable = new JTable(tableModel);
            statisticsTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
            statisticsTable.setRowHeight(20);

            JPanel panel = new JPanel(new BorderLayout(10,10));
            panel.add(new JScrollPane(statisticsTable), BorderLayout.CENTER);

            JLabel label = new JLabel("Prosječna ocjena pogledanih filmova korisnika : " + String.format("%.2f", prosjek), SwingConstants.CENTER);
            panel.add(label, BorderLayout.SOUTH);

            statisticsFrame.add(panel);
            statisticsFrame.setVisible(true);
        });
        setVisible(true);


    }

}
