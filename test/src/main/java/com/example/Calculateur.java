package com.example;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;




public class Calculateur {

    private static final String URL = "jdbc:postgresql://localhost:5432/java";
    private static final String USER = "postgres";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Utilisation: java Calculateur <dossier ou 'bdd'>");
            return;
        }

        String dossier = args[0];
        try {
            if (!dossier.equals("bdd")) {
                verifierDossier(dossier);
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la vérification du dossier : " + e.getMessage());
            return;
        }

        if (dossier.equals("bdd")) {
            // Traitement pour la récupération des données depuis la base de données
            ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
            Reader reader = (Reader) context.getBean("reader");
            reader.read();
        } else {
            // Traitement pour le traitement des fichiers dans un dossier spécifié
            traiterFichiers(new File(dossier));
        }
    }

    private static void verifierDossier(String dossier) throws IOException {
        File dossierFichiers = new File(dossier);
        if (!dossierFichiers.exists() || !dossierFichiers.isDirectory()) {
            throw new IOException("Le dossier spécifié n'existe pas.");
        }
    }

    private static void traiterFichiers(File dossier) {
        File[] fichiers = dossier.listFiles();
        if (fichiers == null || fichiers.length == 0) {
            System.err.println("Le dossier spécifié est vide.");
            return;
        }

        for (File fichier : fichiers) {
            if (fichier.isFile() && fichier.getName().endsWith(".op")) {
                try {
                    GestionnaireFichier.traiterFichier(fichier);
                } catch (IOException e) {
                    System.err.println("Erreur lors du traitement du fichier " + fichier.getName() + ": " + e.getMessage());
                }
            }
        }
    }

    private static void recupererDonneesDepuisBDD() {
        try {
            // Établissement de la connexion à la base de données
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
    
            // Requête SQL pour récupérer les noms des fichiers de type "OP"
            String sql = "SELECT nom FROM fichier WHERE type = 'OP'";
    
            // Préparation de la requête
            PreparedStatement stmt = conn.prepareStatement(sql);
    
            // Exécution de la requête et récupération des résultats
            ResultSet rs = stmt.executeQuery();
    
            // Chemin du dossier de sortie pour les fichiers
            String dossierSortie = "src/main/resources/bdd/";
    
            // Création du dossier de sortie s'il n'existe pas
            File dossierBDD = new File(dossierSortie);
            if (!dossierBDD.exists()) {
                dossierBDD.mkdirs(); // Crée les répertoires parents et le dossier de sortie
            }
    
            // Suppression des fichiers existants dans le dossier de sortie
            while (rs.next()) {
                String nomFichier = rs.getString("nom");
                String nomFichierSortie = dossierSortie + nomFichier + "_resultat.op";
                File fichierSortie = new File(nomFichierSortie);
                if (fichierSortie.exists()) {
                    fichierSortie.delete();
                }
            }
    
            // Fermeture des ressources liées à la requête SQL initiale
            rs.close();
            stmt.close();
    
            // Requête SQL pour récupérer les données des fichiers de type "OP"
            String sqlData = "SELECT f.nom, l.param1, l.param2, l.operateur FROM fichier f INNER JOIN ligne l ON f.id = l.fichier_id WHERE f.type = 'OP'";
    
            // Préparation de la nouvelle requête
            PreparedStatement stmtData = conn.prepareStatement(sqlData);
    
            // Exécution de la nouvelle requête et récupération des résultats
            ResultSet rsData = stmtData.executeQuery();
    
            // Traitement des résultats
            while (rsData.next()) {
                // Récupère les valeurs de chaque colonne
                String nomFichier = rsData.getString("nom");
                double param1 = rsData.getDouble("param1");
                double param2 = rsData.getDouble("param2");
                String operateur = rsData.getString("operateur");
    
                // Ajoute la ligne au contenu du fichier correspondant
                String ligne = param1 + " " + param2 + " " + operateur;
                String nomFichierSortie = dossierSortie + nomFichier + "_resultat.op";
                // Écrit les données dans le fichier de sortie
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomFichierSortie, true))) {
                    writer.write(ligne);
                    writer.newLine();
                } catch (IOException e) {
                    System.err.println("Erreur lors de l'écriture dans le fichier de sortie : " + e.getMessage());
                }
            }
    
            // Fermeture des ressources
            rsData.close();
            stmtData.close();
            conn.close();
    
            // Après avoir récupéré les données, appelle la méthode pour traiter les fichiers
            traiterFichiers(new File(dossierSortie));
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des données depuis la base de données : " + e.getMessage());
        }
    }
    
    

}
