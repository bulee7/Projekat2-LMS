package loginregister;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import java.util.List;
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

    public void azurirajKorisnickoIme(String oldUsername, String newUsername) {
        Document query = new Document("korisnickoIme", oldUsername);
        Document update = new Document("$set", new Document("korisnickoIme", newUsername));
        users.updateOne(query,update);
    }

    public List<User>sviKorisnici() {
        List<User>lista = new ArrayList<>();
        for (Document document : users.find()) {
            User user = new User(document.getString("korisnickoIme"),document.getString("hashLozinka"));
            user.setId(document.getObjectId("_id"));
            lista.add(user);
        }
        return lista;
    }

    public boolean postojiKorisnik(String korisnickoIme){
        Document document = users.find(eq("korisnickoIme",korisnickoIme)).first();
        return document != null;
    }
}
