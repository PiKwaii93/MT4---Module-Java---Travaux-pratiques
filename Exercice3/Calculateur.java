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

        if (operation.equals("-") || operation.equals("+") || operation.equals("x") || operation.equals("/") || operation.equals("%")) {
            try {
                Operation op = OperationFactory.createOperation(operation);
                double result = op.apply(param1, param2);
                System.out.println("Resultat: " + result);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Erreur : Le troisième paramètre doit être '+', '-', '*', '/' ou '%'.");
        }
    }
}
