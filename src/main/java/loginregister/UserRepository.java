package loginregister;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.Optional;

public class UserRepository {
    private final MongoCollection<Document>users;

    public UserRepository() {
        users = MongoDBConnection.getDatabase().getCollection("users");
    }

    public Optional<User>pronadjiPoImenu(String korisnickoIme) {
        Document document = users.find(Filters.eq("korisnickoIme", korisnickoIme)).first();
        if (document != null) {
            User user = new User(document.getString("korisnickoIme"),
                    document.getString("hashLozinka"));
            user.setId(document.getObjectId("_id"));
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public void sacuvaj(User user) {
        Document document = new Document("korisnickoIme", user.getKorisnickoIme()).append("hashLozinka", user.getHashLozinka());
        users.insertOne(document);
        user.setId(document.getObjectId("_id"));
    }

    public void izbrisiPoId(ObjectId id) {
        users.deleteOne(Filters.eq("_id", id));
    }

    public void azurirajLozinku(String korisnickoIme, String novaHashLozinka) {
        users.updateOne(Filters.eq("korisnickoIme", korisnickoIme),
                new Document("$set", new Document("hashLozinka", novaHashLozinka)));
    }
}
