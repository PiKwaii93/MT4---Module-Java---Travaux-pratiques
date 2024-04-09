package Exercice1;

public class Calculateur {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Erreur : Vous devez fournir exactement trois paramètres.");
            return;
        }

        double param1, param2;
        try {
            param1 = Double.parseDouble(args[0]);
            param2 = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Erreur : Les deux premiers paramètres doivent être des nombres.");
            return;
        }

        String operation = args[2];

        if (operation.equals("-") || operation.equals("+") || operation.equals("*") || operation.equals("/") || operation.equals("%") ) {
            double resultat;
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

            System.out.println("Résultat : " + resultat);
        }else{
            System.out.println("Erreur : Le troisième paramètre doit être '+', '-', '*', '/' ou '%'.");
        }

    }
}
