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

    // Cas 2
    public static List<Integer> findOddVertices(Graph g) {
        List<Integer> odd = new ArrayList<>();

        for (int u = 0; u < g.n; u++) {
            if (g.getNeighbors(u).size() % 2 != 0) {
                odd.add(u);
            }
        }
        return odd;
    }
    public static List<Integer> cas2_tournee(Graph g) {

        // 1. Chercher les sommets impairs
        List<Integer> odd = findOddVertices(g);

        if (odd.size() != 2) {
            System.out.println("Erreur : le graphe n'a pas exactement 2 sommets impairs.");
            return null;
        }

        int u = odd.get(0);
        int v = odd.get(1);

        System.out.println("Deux sommets impairs trouvés : " + u + " et " + v);

        // 2. Trouver le plus court chemin entre u et v
        AlgorithmsTheme1Prob1.PathResult pr = AlgorithmsTheme1Prob1.dijkstra(g, u);
        List<Integer> pathUV = AlgorithmsTheme1Prob1.getPath(pr.parent, v);

        System.out.println("Plus court chemin entre " + u + " et " + v + " : " + pathUV);

        // 3. Créer une copie du graphe
        Graph g2 = g.copy(); // IMPORTANT : ne pas modifier le vrai graphe

        // 4. Ajouter les arêtes du chemin u→v
        for (int i = 0; i < pathUV.size() - 1; i++) {
            int a = pathUV.get(i);
            int b = pathUV.get(i + 1);

            // on récupère l’arête réelle pour la pondération
            Edge e = g.getEdge(a, b);

            g2.addEdge(a, b, e.weight, e.streetName);
            g2.addEdge(b, a, e.weight, e.streetName); // non orienté
        }

        // 5. Maintenant tous les sommets sont pairs → cycle eulérien
        List<Integer> cycle = eulerianCycle(g2);

        // 6. Renvoie le cycle final
        return cycle;
    }
    public static void afficherTourneeDetaillee(Graph g, List<Integer> cycle) {
        System.out.println("\n=== Tournée complète (Cas 2) ===");

        double total = 0;

        for (int i = 0; i < cycle.size() - 1; i++) {

            int u = cycle.get(i);
            int v = cycle.get(i + 1);

            Edge chosen = g.getEdge(u, v);

            if (chosen != null) {
                System.out.println(
                        u + " → " + v + " : " +
                                chosen.streetName + " (" + chosen.weight + " m)"
                );

                total += chosen.weight;
            }
        }

        System.out.println("\nDistance totale : " + total + " m\n");
    }








}
