package Exercice2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class FileProcessor {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java FileProcessor <dossier>");
            return;
        }

        String dossier = args[0];
        File dossierFichiers = new File(dossier);

        // Vérifier si le dossier existe
        if (!dossierFichiers.exists() || !dossierFichiers.isDirectory()) {
            System.out.println("Le dossier spécifié n'existe pas.");
            return;
        }

        // Parcourir tous les fichiers dans le dossier
        File[] fichiers = dossierFichiers.listFiles();
        if (fichiers == null || fichiers.length == 0) {
            System.out.println("Le dossier spécifié est vide.");
            return;
        }

        for (File fichier : fichiers) {
            if (fichier.isFile() && fichier.getName().endsWith(".op")) {
                processFile(fichier);
            }
        }
    }

    private static void processFile(File fichier) {
        String nomFichier = fichier.getName().replaceAll("calcul", "resultat");;
        String nomFichierResultat = nomFichier.replace(".op", ".res");

        try (BufferedReader reader = new BufferedReader(new FileReader(fichier));
             BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fichier.getParent(), nomFichierResultat)))) {

            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] elements = ligne.split(" ");
                    if (elements.length != 3) {
                        writer.write("Dans " + fichier.getName() + ", la syntaxe de la ligne est invalide : " + ligne);
                    }

                    double param1, param2;
                    try {
                        param1 = Double.parseDouble(elements[0]);
                        param2 = Double.parseDouble(elements[1]);
                    } catch (NumberFormatException e) {
                        writer.write("Erreur : Les deux premiers paramètres doivent être des nombres.");
                        return;
                    }
            
                    String operation = elements[2];

                    double resultat = 0;
                    switch (operation) {
                        case "+":
                            resultat = param1 + param2;
                            break;
                        case "-":
                            resultat = param1 - param2;
                            break;
                        case "*":
                            resultat = param1 * param2;
                            break;
                        case "/":
                            if (param2 == 0) {
                                System.out.println("Erreur : Division par zéro.");
                                return;
                            }
                            resultat = param1 / param2;
                            break;
                        case "%":
                            if (param2 == 0) {
                                System.out.println("Erreur : Modulo par zéro.");
                                return;
                            }
                            resultat = param1 % param2;
                            break;
                        default:
                            System.out.println("Erreur : Opérateur non reconnu.");
                            return;
                    }

                    writer.write(Double.toString(resultat));
                    writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
