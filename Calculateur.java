import java.io.*;

public class Calculateur {
    public static void main(String[] args) {
        if (!validerArguments(args)) {
            return;
        }

        String dossier = args[0];
        if (!verifierDossier(dossier)) {
            return;
        }

        traiterFichiers(new File(dossier));
    }

    private static boolean validerArguments(String[] args) {
        if (args.length != 1) {
            System.out.println("Utilisation: java Calculateur <dossier>");
            return false;
        }
        return true;
    }

    private static boolean verifierDossier(String dossier) {
        File dossierFichiers = new File(dossier);
        if (!dossierFichiers.exists() || !dossierFichiers.isDirectory()) {
            System.err.println("Le dossier spécifié n'existe pas.");
            return false;
        }
        return true;
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
}
