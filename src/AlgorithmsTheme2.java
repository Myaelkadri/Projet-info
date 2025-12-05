import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.*;

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


// 1. PRIM — Construction de l’Arbre Couvrant Minimum (MST)


    public static List<int[]> primMST(Graph g) {
        int n = g.n;
        boolean[] visited = new boolean[n];
        double[] minWeight = new double[n];
        int[] parent = new int[n];


        Arrays.fill(minWeight, Double.MAX_VALUE);
        Arrays.fill(parent, -1);


        minWeight[0] = 0; // départ arbitraire


        for (int i = 0; i < n; i++) {


            // 1) choisir le sommet non visité le plus proche
            int u = -1;
            for (int v = 0; v < n; v++) {
                if (!visited[v] && (u == -1 || minWeight[v] < minWeight[u])) {
                    u = v;
                }
            }


            visited[u] = true;


            // 2) relaxation des arêtes
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




    // 2. Construire la liste d’adjacence du MST
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


    // 3. DFS Préfixe pour donner un ordre de visite
    private static void dfs(int u, boolean[] visited, List<Integer> order, List<List<Integer>> adj) {
        visited[u] = true;
        order.add(u);


        for (int v : adj.get(u)) {
            if (!visited[v]) dfs(v, visited, order, adj);
        }
    }




    // 4. Shortcutting : supprimer les répétitions
    private static List<Integer> shortcut(List<Integer> order) {
        Set<Integer> seen = new HashSet<>();
        List<Integer> result = new ArrayList<>();


        for (int x : order) {
            if (!seen.contains(x)) {
                result.add(x);
                seen.add(x);
            }
        }


        result.add(order.get(0)); // retour au dépôt


        return result;
    }




    //  MST + DFS + SHORTCUTTING
    public static List<Integer> computeMSTRoute(Graph g) {


        // 1. Construire le MST
        List<int[]> mst = primMST(g);


        // 2. Construire l'adjacence du MST
        List<List<Integer>> adj = buildMSTAdj(g.n, mst);


        // 3. DFS préfixe depuis le dépôt 0
        boolean[] visited = new boolean[g.n];
        List<Integer> order = new ArrayList<>();
        dfs(0, visited, order, adj);


        // 4. Shortcutting
        List<Integer> finalRoute = shortcut(order);


        return finalRoute;
    }




    // 5. Affichage
    public static void afficherTourneeMST(Graph g, List<Integer> route) {


        double total = 0;


        System.out.println("\n=== Tournée Approche 2 (MST + DFS + Shortcutting) ===\n");


        for (int i = 0; i < route.size() - 1; i++) {
            int u = route.get(i);
            int v = route.get(i + 1);


            Edge e = g.getEdge(u, v);


            if (e != null) {
                System.out.println(
                        " Depuis " + u + " aller vers " + v +
                                " via " + e.streetName +
                                " (" + e.weight + " m)"
                );
                total += e.weight;
            } else {
                System.out.println(" Depuis " + u + " aller vers " + v + " (pas d'arête directe)");
            }
        }


        System.out.println("\n Distance totale : " + total + " m\n");
    }




}

