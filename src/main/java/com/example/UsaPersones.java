package com.example;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;

import org.bson.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UsaPersones {
    MongoClient mongoClient = null; // Java MongoDB client
    MongoDatabase db = null; // Database object
    MongoCollection<Document> coleccio = null;

    public void connectDatabase() {
        mongoClient = new MongoClient();
        db = mongoClient.getDatabase("nouTest");
        coleccio = db.getCollection("persones");
        System.out.println("conectat a la BD " + db.getName());
    }

    public void inserirUnaPersona(Persona persona) {
        try {
            coleccio.insertOne(
                    new Document("nom", persona.getNom())
                            .append("edat", persona.getEdat())
                            .append("professió", persona.getProfessio())
                            .append("sou", persona.getSou())
                            .append("adreça", (new Document()
                                    .append("carrer", persona.getAdreça().getCarrer())
                                    .append("població", persona.getAdreça().getPoblacio())
                                    .append("localitat", persona.getAdreça().getLocalitat())
                                    .append("província", persona.getAdreça().getProvincia())
                                    .append("codi postal", persona.getCodiPostal()))));
            System.out.println("s'ha inserit " + persona.getNom() + " correctament");
        } catch (Exception e) {
            System.out.println("no s'ha pogut inserir la persona " + persona.getNom());
        }
    }

    public void mostrarTotesLesPersones() {
        FindIterable<Document> docs = coleccio.find();
        for (Document doc : docs) {
            System.out.println(doc.toJson());
        }
    }

    public Boolean exiteixUnaPersonaAmbNom(String nom) {
        Document query = new Document("nom", nom);
        long numResults = coleccio.countDocuments(query);
        if (numResults > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void mostrarPersonesAmbEdat(int min, int max) {
        Document query = new Document("edat", new Document("$gte", min).append("$lte", max));
        long numResults = coleccio.countDocuments(query);
        if (numResults != 0) {
            System.out.println("s'ha trobat " + numResults + " en aquest rang d'edats");
            FindIterable<Document> docs = coleccio.find(query);
            for (Document doc : docs) {
                System.out.println(doc.toJson());
            }
        } else {
            System.out.println("no s'ha trobat cap persones en aquest rang d'edats");
        }
    }

    public void mostrarPersonesAmb(String clau, String valor) {
        Document query = new Document(clau, valor);
        long numResults = coleccio.countDocuments(query);
        if (numResults != 0) {
            System.out.println("s'ha trobat " + numResults + "persones que cumpleixen {" + clau + " : " + valor + "}");
            FindIterable<Document> docs = coleccio.find(query);
            for (Document doc : docs) {
                System.out.println(doc.toJson());
            }
        } else {
            System.out.println("no s'ha trobat cap persones que cumpleixen {" + clau + " : " + valor + "}");
        }
    }

    public void editarEdatDePersonesAmb(String clau, String valor, int novaEdat) {
        Document query = new Document(clau, valor);
        Document update = new Document("$set", new Document("edat", novaEdat));
        Document doc = coleccio.findOneAndUpdate(query, update);
        if (doc == null) {
            System.out.println("no s'ha trobat cap persones que cumpleixen {" + clau + " : " + valor + "}");
        } else {
            System.out.println("s'ha actualitzat l'edat de" + valor + ". La nova edat és " + novaEdat);
        }
    }

    public void mostrarEdatDePersonesAmb(String clau, String valor) {
        Document query = new Document(clau, valor);
        System.out.println(query.toJson());
        long numResults = coleccio.countDocuments(query);
        if (numResults != 0) {
            System.out.println("s'ha trobat " + numResults + "persones que cumpleixen {" + clau + " : " + valor + "}");
            FindIterable<Document> docs = coleccio.find(query);
            for (Document doc : docs) {
                System.out.println("edat de " + clau + " = " + doc.getInteger("edat"));
            }
        } else {
            System.out.println("no s'ha trobat cap persones que cumpleixen {" + clau + " : " + valor + "}");
        }
    }

    public void carregarLlistaDePersones(File arxiu) {
        try {
            Scanner lector = new Scanner(arxiu, "UTF-8");
            List<Document> llistaDocs = new ArrayList<Document>();
            while (lector.hasNext()) {
                System.out.println(lector.nextLine());
                try {
                    String contingutDoc = lector.nextLine();
                    Document doc = Document.parse(contingutDoc);
                    llistaDocs.add(doc);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            coleccio.insertMany(llistaDocs);
            lector.close();
            System.out.println("s'han inserit " + llistaDocs.size() + " documents amb èxit.");
        } catch (FileNotFoundException e) {
            System.out.println("no s'ha pogut carregat l'arxiu" + arxiu.getAbsolutePath() + e);
        }

    }

    public void mostrarAdreçaDePersonesAmb(String clau, String valor) {
        Document query = new Document(clau, valor);
        System.out.println(query.toJson());
        long numResults = coleccio.countDocuments(query);
        if (numResults != 0) {
            System.out.println("s'ha trobat " + numResults + "persones que cumpleixen {" + clau + " : " + valor + "}");
            FindIterable<Document> docs = coleccio.find(query);
            for (Document doc : docs) {
                System.out.println(doc.get("adreça"));
            }
        } else {
            System.out.println("no s'ha trobat cap persones que cumpleixen {" + clau + " : " + valor + "}");
        }
    }

    public void mostrarPersonesAmbElNom(String nom) {
        Integer totalCount = (int) coleccio.countDocuments(new Document("nom", nom));
        System.out.println("s'han trobat " + totalCount + " persones amb el nom " + nom);
        FindIterable<Document> docs = coleccio.find(new Document("nom", nom));

        for (Document doc : docs) {
            System.out.println(doc.toJson());
        }
    }

    public List<Persona> obtenirPersonesAmbElNom(String nom) {
        List<Persona> llistaPersones = new ArrayList<Persona>();
        FindIterable<Document> docs = coleccio.find(new Document("nom", nom));

        for (Document doc : docs) {
            // accedir a un subdocument
            Document adr = doc.get("adreça", Document.class);
            Adreça novaAdreça = new Adreça(adr.getString("carrer"), adr.getString("població"),
                    adr.getString("localitat"), adr.getString("província"));
            Persona persona = new Persona(doc.getString("nom"), doc.getInteger("edat"), doc.getString("professió"),
                    doc.getInteger("sou"), novaAdreça, doc.getString("codi postal"));
            llistaPersones.add(persona);
        }
        return llistaPersones;
    }

    public void ContarTotesLesPersones() {
        // Conta tots els documents de la col·lecció
        int numDocuments = (int) coleccio.countDocuments();
        System.out.println("Número de documents en la colecció Persones: " + numDocuments + "\n");
    }

    public void BorrarPersonaAmbElNom(String nom) {
        Document findDoc = new Document("nom", nom);
        DeleteResult num = coleccio.deleteMany(findDoc);
        if (num.wasAcknowledged()) {
            System.out.println("s'han eliminat les persones amb {nom:+" + nom + "}");
        } else {
            System.out.println("no s'ha pogut realitzar l'operació");
        }
    }

    public void TancarConnexioMongo() {// Tancar la conexio
        mongoClient.close();
    }

    public static void main(String args[]) {
        UsaPersones usaPersones = new UsaPersones();
        usaPersones.connectDatabase();

        // Creem una nova persona i la guardem a la BD
        Adreça novaAdreça = new Adreça("carrer nou", "Badalona", "Badalona",
                "Barcelona");
        Persona personaNova = new Persona("Paco Poco Pico", 22, "paleta", 111,
                novaAdreça, "8123");
        usaPersones.inserirUnaPersona(personaNova);

        // mostrar per consola totes les persones
        // usaPersones.mostrarTotesLesPersones();

        // mostrar per consola les persones amb el nom "Paco Poco Pico"
        // usaPersones.mostrarPersonesAmbElNom("Joan Joen Join");

        // obtenir llista de persones amb nom="Paco Poco Pico".
        // Iterem la llista i mostrem per consola el nom de cada una
        List<Persona> persones = usaPersones.obtenirPersonesAmbElNom("Paco Poco Pico");
        for (int i = 0; i < persones.size(); i++) {
            System.out.println(persones.get(i).getNom());
        }

        usaPersones.ContarTotesLesPersones();

        usaPersones.BorrarPersonaAmbElNom("Paco Poco Pico");

        usaPersones.TancarConnexioMongo();
    }
}