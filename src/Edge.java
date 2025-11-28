public class Edge {
    public int to;            // sommet d’arrivée
    public double weight;     // distance / poids
    public String streetName; // nom de la rue

    public Edge(int to, double weight, String streetName) {
        this.to = to;
        this.weight = weight;
        this.streetName = streetName;
    }

    @Override
    public String toString() {
        return "-> " + to + " (" + weight + "m, " + streetName + ")";
    }
}
