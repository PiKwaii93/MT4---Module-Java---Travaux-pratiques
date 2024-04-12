package com.example;

public class Reader {
    private String implementation;

    // Setter pour l'implémentation
    public void setImplementation(String implementation) {
        this.implementation = implementation;
    }

    // Méthode pour lire selon l'implémentation spécifiée
    public void read() {
        if ("fileSystem".equals(implementation)) {
            System.out.println("Lecture depuis le système de fichiers.");
            // Ajoutez votre logique pour lire depuis le système de fichiers ici
        } else if ("database".equals(implementation)) {
            System.out.println("Lecture depuis la base de données.");
            // Ajoutez votre logique pour lire depuis la base de données ici
        } else {
            System.out.println("Aucune implémentation spécifiée.");
        }
    }
}
