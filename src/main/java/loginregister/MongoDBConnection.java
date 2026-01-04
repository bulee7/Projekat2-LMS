package loginregister;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {

    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "LifeManagementSystem";
    private static final MongoClient mongoClient = MongoClients.create(CONNECTION_STRING);
    private static final MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

    public static MongoDatabase getDatabase() {
        return database;
    }
}
