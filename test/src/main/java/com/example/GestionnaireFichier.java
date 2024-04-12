package com.example;

import java.io.*;

public class GestionnaireFichier {
    public static void traiterFichier(File fichier) throws IOException {
        String nomFichierSortie = fichier.getName().replaceAll("calcul", "resultat").replace(".op", ".res");

        try (BufferedReader lecteur = new BufferedReader(new FileReader(fichier));
             BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fichier.getParent(), nomFichierSortie)))) {

            String ligne;
            while ((ligne = lecteur.readLine()) != null) {
                String[] elements = ligne.split(" ");
                if (!validerParametres(elements, writer, fichier.getName())) {
                    continue;
                }

                double param1, param2;
                try {
                    param1 = Double.parseDouble(elements[0]);
                    param2 = Double.parseDouble(elements[1]);
                    String operateur = elements[2];

                    Operation operation = OperationFactory.creerOperation(operateur);
                    double resultat = operation.calculer(param1, param2);

                    writer.write(Double.toString(resultat));
                    writer.newLine();
                } catch (NumberFormatException e) {
                    gestionnerException(e, writer, "Erreur : Les deux premiers paramètres doivent être des nombres.");
                } catch (IllegalArgumentException e) {
                    gestionnerException(e, writer, "Erreur : Opérateur non reconnu : " + e.getMessage());
                }
            }
        } catch (IOException e) {
            gestionnerException(e, null, "Erreur lors de la lecture/écriture du fichier.");
        }
    }

    private static boolean validerParametres(String[] elements, BufferedWriter writer, String fileName) throws IOException {
        if (elements.length != 3) {
            writer.write("Dans " + fileName + ", la syntaxe de la ligne est invalide : " + String.join(" ", elements));
            writer.newLine();
            return false;
        }
        return true;
    }

    private static void gestionnerException(Exception e, BufferedWriter writer, String message) throws IOException {
        e.printStackTrace();
        if (writer != null) {
            writer.write(message);
            writer.newLine();
        }
    }
}
