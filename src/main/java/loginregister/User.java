package loginregister;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

public class User {
    @BsonId
    private ObjectId id;
    private String korisnickoIme;
    private String hashLozinka;

    public User() {}

    public User(String korisnickoIme, String hashLozinka) {
        this.korisnickoIme = korisnickoIme;
        this.hashLozinka = hashLozinka;
    }

    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }

    public String getKorisnickoIme() { return korisnickoIme; }
    public String getHashLozinka() { return hashLozinka; }
}
