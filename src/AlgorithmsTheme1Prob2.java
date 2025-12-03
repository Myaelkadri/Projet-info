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


    // Cas 3
// ----------- Trouver les sommets impairs -----------
    public static List<Integer> sommetsImpairs(Graph g) {
        List<Integer> odd = new ArrayList<>();
        for (int u = 0; u < g.n; u++) {
            if (g.getNeighbors(u).size() % 2 != 0) {
                odd.add(u);
            }
        }
        return odd;
    }


    // ----------- Générer tous les appariements possibles des sommets impairs -----------
    private static List<int[]> genererAppariements(List<Integer> odd) {


        List<int[]> res = new ArrayList<>();


        if (odd.isEmpty()) return res;


        int a = odd.get(0);


        if (odd.size() == 2) {
            res.add(new int[]{odd.get(0), odd.get(1)});
            return res;
        }


        for (int i = 1; i < odd.size(); i++) {


            int b = odd.get(i);


            List<Integer> rest = new ArrayList<>(odd);
            rest.remove(Integer.valueOf(a));
            rest.remove(Integer.valueOf(b));


            List<int[]> sub = genererAppariements(rest);


            for (int[] p : sub) {
                int[] pair = new int[p.length + 2];
                pair[0] = a;
                pair[1] = b;
                System.arraycopy(p, 0, pair, 2, p.length);
                res.add(pair);
            }
        }


        return res;
    }


    // ----------- Distance via Dijkstra (déjà codé dans prob 1) -----------
    private static double dijkstraDistance(Graph g, int src, int dest) {
        AlgorithmsTheme1Prob1.PathResult pr = AlgorithmsTheme1Prob1.dijkstra(g, src);
        return pr.dist[dest];
    }


    // ----------- Chemin via Dijkstra -----------
    private static List<Integer> dijkstraPath(Graph g, int src, int dest) {
        AlgorithmsTheme1Prob1.PathResult pr = AlgorithmsTheme1Prob1.dijkstra(g, src);
        return AlgorithmsTheme1Prob1.getPath(pr.parent, dest);
    }


    // ----------- Trouver le poids et nom de rue d'une arête -----------
    private static double findWeight(Graph g, int u, int v) {
        for (Edge e : g.getNeighbors(u)) {
            if (e.to == v) return e.weight;
        }
        return 0;
    }


    private static String findStreet(Graph g, int u, int v) {
        for (Edge e : g.getNeighbors(u)) {
            if (e.to == v) return e.streetName;
        }
        return "";
    }


    public static List<Integer> cas3_CPP(Graph g) {


        // 1. Récupération des sommets impairs
        List<Integer> odd = sommetsImpairs(g);


        if (odd.size() == 0) {
            System.out.println("Cas 1 détecté : tous les sommets sont pairs → cycle eulérien.");
            return eulerianCycle(g);
        }


        if (odd.size() == 2) {
            System.out.println("Cas 2 détecté : exactement 2 sommets impairs.");
            return cas2_tournee(g);
        }


        System.out.println("\n Cas 3 détecté : " + odd.size() + " sommets impairs.");
        // 2. Générer tous les appariements
        List<int[]> pairings = genererAppariements(odd);


        double best = Double.MAX_VALUE;
        int[] bestPairing = null;


        // 3. Tester chaque appariement
        for (int[] P : pairings) {


            double sum = 0;


            for (int i = 0; i < P.length; i += 2) {
                sum += dijkstraDistance(g, P[i], P[i + 1]);
            }


            if (sum < best) {
                best = sum;
                bestPairing = P;
            }
        }


        System.out.println("Meilleur appariement trouvé : " + Arrays.toString(bestPairing));


        // 4. Dupliquer les chemins correspondant à l’appariement
        Graph g2 = g.copy();


        for (int i = 0; i < bestPairing.length; i += 2) {


            int a = bestPairing[i];
            int b = bestPairing[i + 1];


            List<Integer> path = dijkstraPath(g, a, b);


            for (int j = 0; j < path.size() - 1; j++) {


                int u = path.get(j);
                int v = path.get(j + 1);


                double w = findWeight(g, u, v);
                String s = findStreet(g, u, v);


                g2.addEdge(u, v, w, s);
                g2.addEdge(v, u, w, s);
            }
        }


        // 5. Tous les sommets sont pairs → cycle eulérien final
        return eulerianCycle(g2);
    }


    // ----------- AFFICHAGE -----------
    public static void afficher(Graph g, List<Integer> cycle) {


        System.out.println("\n=== Itinéraire ===\n");


        double total = 0;


        for (int i = 0; i < cycle.size() - 1; i++) {


            int u = cycle.get(i);
            int v = cycle.get(i + 1);


            Edge chosen = g.getEdge(u, v);


            String from = "" + u;
            String to   = "" + v;


            if (chosen != null) {
                System.out.println(
                        " Depuis " + from + " allez vers " + to +
                                " par " + chosen.streetName +
                                "(" + chosen.weight + " m)"
                );


                total += chosen.weight;


            } else {
                System.out.println(
                        " Depuis " + from + " allez vers " + to +
                                " (arête dupliquée pour la tournée)"
                );
            }
        }


        System.out.println("\n Distance totale : " + total + " m \n");
    }

}
