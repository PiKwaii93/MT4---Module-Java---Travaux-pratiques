public class OperationFactory {
    public static Operation createOperation(String operation) {
        switch (operation) {
            case "+":
                return new Addition();
            case "-":
                return new Soustraction();
            case "x":
                return new Multiplication();
            case "/":
                return new Division();
            case "%":
                return new Modulo();
            default:
                throw new IllegalArgumentException("Erreur : Opérateur non reconnu.");
        }
    }
}
