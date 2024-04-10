public class Modulo implements Operation {
    public double apply(double param1, double param2) {
        if (param2 == 0) {
            throw new ArithmeticException("Erreur : Modulo par zéro.");
        }
        return param1 % param2;
    }
}
