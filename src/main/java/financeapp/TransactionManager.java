package financeapp;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.ArrayList;

public class TransactionManager {
    private final MongoCollection<Document> collection;

    public TransactionManager() {
        MongoDatabase db = MongoDBConnection.getDatabase();
        collection = db.getCollection("transactions");
    }

    public void addTransaction(Transaction t) {
        collection.insertOne(t.toDocument());
    }

    public ArrayList<Transaction> getAllTransactions() {
        ArrayList<Transaction> list = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document d = cursor.next();

                String type = d.getString("Vrsta");
                String description = d.getString("Opis");
                double amount = getDoubleFromDocument(d, "Iznos");

                list.add(new Transaction(type,amount,description));
            }
        }
        return list;
    }

    private double getDoubleFromDocument(Document doc, String key) {
        Object value = doc.get(key);
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        } else if (value instanceof String) {
            try {
                return Double.parseDouble((String) value);
            } catch (NumberFormatException e) {
                return 0.0;
            }
        }
        return 0.0;
    }

    public double getTotalIncome() {
        return getAllTransactions().stream()
                .filter(t -> "Prihod".equals(t.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getTotalExpense() {
        return getAllTransactions().stream()
                .filter(t -> "Rashod".equals(t.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
}

