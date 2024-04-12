public class Modulo implements Operation {
    public double calculer(double param1, double param2) {
        if (param2 == 0) {
            System.out.println("Erreur : Modulo par zéro.");
            return Double.NaN;
        }
        return param1 % param2;
    }
}
