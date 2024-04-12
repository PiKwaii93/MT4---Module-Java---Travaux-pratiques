package com.example;

public class OperationFactory {
    public static Operation creerOperation(String operateur) {
        switch (operateur) {
            case "+":
                return new Addition();
            case "-":
                return new Soustraction();
            case "*":
                return new Multiplication();
            case "/":
                return new Division();
            case "%":
                return new Modulo();
            case "^":
                return new Exposant();
            default:
                throw new IllegalArgumentException("Opérateur non reconnu : " + operateur);
        }
    }
}
