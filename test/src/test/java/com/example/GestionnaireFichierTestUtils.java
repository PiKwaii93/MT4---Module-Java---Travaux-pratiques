package com.example;

import java.io.*;

public class GestionnaireFichierTestUtils {
    public static void ecrireOperationDansFichier(File fichier, String operation) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichier))) {
            writer.write(operation);
        }
    }

    public static String lirePremiereLigneFichierResultat(File fichier) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
            return reader.readLine();
        }
    }
}
