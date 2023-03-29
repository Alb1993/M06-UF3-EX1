/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package albert.m06uf3ex1;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.bson.Document;

/**
 *
 * @author Albert
 */
public class MongoSession {
    public static MongoClient mongoClient;
    public static MongoDatabase bbdd;
    public static MongoCollection<Document> coleccio;
    public static MongoSession instance = null;

    public MongoSession() {
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
        mongoClient = MongoClients.create(connectionString);
        bbdd = mongoClient.getDatabase("exemple1Database");
        coleccio = bbdd.getCollection("testCollection");
    }
    
    public static MongoSession getInstance() {
        if (instance == null) {
            instance = new MongoSession();
        }
        return instance;
    }
    
    public MongoCollection<Document> getColleccio() {
        return coleccio;
    }

}
