package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;

public class CalculateurTest {

    @Test
    public void testTraitementFichiers() {
        // Créer un fichier de test contenant une opération
        File fichierTest = new File("src/test/resources/basic.op");
        try {
            // Écrire une opération dans le fichier de test
            GestionnaireFichierTestUtils.ecrireOperationDansFichier(fichierTest, "10 2 +");

            // Appeler la méthode de traitement des fichiers
            Calculateur.traiterFichiers(fichierTest.getParentFile());

            // Vérifier le résultat dans le fichier de sortie
            File fichierResultat = new File("src/test/resources/basic.res");
            String resultat = GestionnaireFichierTestUtils.lirePremiereLigneFichierResultat(fichierResultat);
            
            assertEquals("12.0", resultat);
        } catch (IOException e) {
            fail("Erreur lors de l'écriture ou de la lecture du fichier : " + e.getMessage());
        }
    }
}
