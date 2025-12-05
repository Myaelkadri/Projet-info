import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.*

public class AlgorithmsTheme2 {
    /**
     * Calcule un ordre de visite des points selon l'approche
     * du Plus Proche Voisin (Nearest Neighbor).
     *
     * @param graph  : votre graphe (non orienté pour HO1)
     * @param start  : sommet de départ (le dépôt D)
     * @param points : liste des points de collecte (indices de sommets)
     * @return liste ordonnée des sommets visités (tournée)
    */

    public List<Integer> computeRoute(Graph graph, int start, List<Integer> points) {

        // copie pour éviter de modifier la liste d'origine
        Set<Integer> unvisited = new HashSet<>(points);
        List<Integer> route = new ArrayList<>();

        int current = start;
        route.add(current);

        // Tant qu'il reste des points non visités
        while (!unvisited.isEmpty()) {
            int next = findNearest(graph, current, unvisited);

            if (next == -1) {
                // Erreur : pas de point atteignable
                break;
            }

            route.add(next);
            unvisited.remove(next);
            current = next;
        }

        // Retour au dépôt
        route.add(start);

        return route;
    }


    /**
     * Retourne le point non visité le plus proche du sommet "current".
     *
     * @param graph     : votre graphe
     * @param current   : sommet actuel
     * @param unvisited : ensemble des points à visiter
     * @return sommet le plus proche dans unvisited
    */
   
    private int findNearest(Graph graph, int current, Set<Integer> unvisited) {
        double bestDist = Double.POSITIVE_INFINITY;
        int bestNode = -1;

        for (int candidate : unvisited) {
            Double w = graph.getWeight(current, candidate);
            if (w != null && w < bestDist) {
                bestDist = w;
                bestNode = candidate;
            }
        }

        return bestNode;
    }




    // Approche 2
    public static List<int[]> primMST(Graph g) {
        int n = g.n;
        boolean[] visited = new boolean[n];
        double[] minWeight = new double[n];
        int[] parent = new int[n];


        Arrays.fill(minWeight, Double.MAX_VALUE);
        Arrays.fill(parent, -1);


        minWeight[0] = 0;  // départ arbitraire


        for (int i = 0; i < n; i++) {
            int u = -1;


            // 1) chercher le sommet non visité avec le minWeight le plus faible
            for (int v = 0; v < n; v++) {
                if (!visited[v] && (u == -1 || minWeight[v] < minWeight[u])) {
                    u = v;
                }
            }


            visited[u] = true;


            // 2) relâcher toutes les arêtes sortantes
            for (Edge e : g.getNeighbors(u)) {
                if (!visited[e.to] && e.weight < minWeight[e.to]) {
                    minWeight[e.to] = e.weight;
                    parent[e.to] = u;
                }
            }
        }


        // 3) Construire la liste des arêtes du MST
        List<int[]> mstEdges = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            mstEdges.add(new int[]{parent[i], i});
        }


        return mstEdges;
    }


    private static List<List<Integer>> buildMSTAdj(int n, List<int[]> mstEdges) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());


        for (int[] e : mstEdges) {
            int u = e[0];
            int v = e[1];
            adj.get(u).add(v);
            adj.get(v).add(u);
        }
        return adj;
    }


    private static void dfsMST(int u, boolean[] visited, List<Integer> order, List<List<Integer>> mstAdj) {
        visited[u] = true;
        order.add(u);


        for (int v : mstAdj.get(u)) {
            if (!visited[v]) dfsMST(v, visited, order, mstAdj);
        }
    }


    public static List<Integer> approcheMST(Graph g) {


        // 1. calculer l’arbre couvrant
        List<int[]> mst = primMST(g);


        // 2. construire la liste d’adjacence du MST
        List<List<Integer>> mstAdj = buildMSTAdj(g.n, mst);


        // 3. faire un parcours DFS depuis le sommet 0
        List<Integer> order = new ArrayList<>();
        boolean[] visited = new boolean[g.n];
        dfsMST(0, visited, order, mstAdj);


        return order;
    }

    public static void afficherTournee(Graph g, List<Integer> order) {

        double total = 0;

        System.out.println("\n=== Tournée Approche MST ===");


        for (int i = 0; i < order.size() - 1; i++) {
            int u = order.get(i);
            int v = order.get(i+1);


            Edge e = g.getEdge(u, v);
            System.out.println(u + " → " + v + "   (" + e.weight + " m)");
            total += e.weight;
        }
        System.out.println("Distance totale : " + total + " m");
    }
}

