import java.util.*;


public class AlgorithmsTheme1Prob2 {


    // ---------- Vérifier si tous les sommets ont un degré pair ----------
    public static boolean isEulerian(Graph g) {
        for (int u = 0; u < g.n; u++) {
            if (g.getNeighbors(u).size() % 2 != 0) {
                return false;
            }
        }
        return true;
    }


    // ---------- Cycle eulérien (Hierholzer) ----------
    public static List<Integer> eulerianCycle(Graph g) {
        Stack<Integer> stack = new Stack<>();
        List<Integer> circuit = new ArrayList<>();


        // On commence au sommet 0 (ou un autre sommet non isolé)
        int start = 0;
        stack.push(start);


        // copie des listes d’adjacence pour ne pas détruire le graphe d’origine
        List<List<Edge>> adjCopy = new ArrayList<>();
        for (int u = 0; u < g.n; u++) {
            adjCopy.add(new ArrayList<>(g.getNeighbors(u)));
        }


        while (!stack.isEmpty()) {
            int u = stack.peek();


            if (!adjCopy.get(u).isEmpty()) {
                Edge e = adjCopy.get(u).remove(0);  // on enlève l’arête (u → v)
                // enlever aussi l’arête dans l’autre sens
                adjCopy.get(e.to).removeIf(ed -> ed.to == u);


                stack.push(e.to);
            } else {
                circuit.add(stack.pop());
            }
        }

        Collections.reverse(circuit);
        return circuit;
    }

    // ---------- Calcul de la distance totale + affichage détaillé ----------
    public static void afficherTournee(Graph g, List<Integer> cycle) {


        double total = 0;

        System.out.println("\n=== Tournée Eulérienne complète ===");

        for (int i = 0; i < cycle.size() - 1; i++) {


            int u = cycle.get(i);
            int v = cycle.get(i + 1);


            // chercher l’arête u→v pour trouver rue + distance
            Edge chosen = null;
            for (Edge e : g.getNeighbors(u)) {
                if (e.to == v) {
                    chosen = e;
                    break;
                }
            }

            if (chosen != null) {
                total += chosen.weight;


                System.out.println("Depuis " + u +
                        " → prendre " + chosen.streetName +
                        " → aller à " + v +
                        " (" + chosen.weight + " m)");
            }
        }
        System.out.println("Distance totale parcourue : " + total + " m");
    }
}
