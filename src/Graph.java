import java.util.*;

public class Graph {

    int n;  // nombre de sommets
    private List<List<Edge>> adj;  // liste d’adjacence
    private boolean oriented = false; // utile si tu veux gérer les sens uniques

    public Graph(int n) {
        this.n = n;
        this.adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void setOriented(boolean o) {
        this.oriented = o;
    }

    public int getNbVertices() {
        return n;
    }

    // -------------------------
    //   Ajouter une arête
    // -------------------------
    public void addEdge(int u, int v, double w, String street) {
        adj.get(u).add(new Edge(v, w, street));
    }

    // -------------------------
    //   Obtenir les voisins
    // -------------------------
    public List<Edge> getNeighbors(int u) {
        return adj.get(u);
    }

    // -------------------------
    //   Obtenir le poids entre u et v
    // -------------------------
    public Double getWeight(int u, int v) {
        for (Edge e : adj.get(u)) {
            if (e.to == v) return e.weight;
        }
        return null;
    }

    // -------------------------
    //   Obtenir l’arête entre u et v
    // -------------------------
    public Edge getEdge(int u, int v) {
        for (Edge e : adj.get(u)) {
            if (e.to == v) return e;
        }
        return null;
    }

    // -------------------------
    //   Calcul du degré d’un sommet
    // -------------------------
    public int degree(int u) {
        return adj.get(u).size();
    }

    // -------------------------
    //   Trouver les sommets impairs
    // -------------------------
    public List<Integer> getOddVertices() {
        List<Integer> odd = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (degree(i) % 2 == 1) {
                odd.add(i);
            }
        }
        return odd;
    }

    // -------------------------
    //   Affichage du graphe
    // -------------------------
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Graphe avec ").append(n).append(" sommets\n");

        for (int u = 0; u < n; u++) {
            sb.append(u).append(" : ");
            for (Edge e : adj.get(u)) {
                sb.append(" -> ").append(e.to)
                        .append(" (").append(e.weight).append("m, ")
                        .append(e.streetName).append(") ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
