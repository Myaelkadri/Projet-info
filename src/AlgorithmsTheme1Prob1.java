import java.util.*;

public class AlgorithmsTheme1Prob1 {

    // -------------------------------------------------------------------------
    // Résultat de Dijkstra : distances + parents
    // -------------------------------------------------------------------------
    public static class PathResult {
        public double[] dist;
        public int[] parent;

        public PathResult(double[] dist, int[] parent) {
            this.dist = dist;
            this.parent = parent;
        }
    }

    // -------------------------------------------------------------------------
    // 1) Dijkstra classique
    // -------------------------------------------------------------------------
    public static PathResult dijkstra(Graph g, int source) {

        double[] dist = new double[g.n];
        int[] parent = new int[g.n];
        boolean[] visited = new boolean[g.n];

        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        Arrays.fill(parent, -1);

        dist[source] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingDouble(a -> a[1]));
        pq.add(new int[]{source, 0}); // {sommet, distance}

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int u = current[0];

            if (visited[u]) continue;
            visited[u] = true;

            for (Edge e : g.getNeighbors(u)) {
                int v = e.to;
                double w = e.weight;

                if (dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    parent[v] = u;
                    pq.add(new int[]{v, (int) dist[v]});
                }
            }
        }

        return new PathResult(dist, parent);
    }

    // -------------------------------------------------------------------------
    // 2) Reconstruire un chemin depuis source → cible
    // -------------------------------------------------------------------------
    public static List<Integer> getPath(int[] parent, int cible) {

        List<Integer> path = new ArrayList<>();
        int curr = cible;

        while (curr != -1) {
            path.add(curr);
            curr = parent[curr];
        }

        Collections.reverse(path);
        return path;
    }

    // -------------------------------------------------------------------------
    // 3) Affichage détaillé : rues, distances, temps estimé
    // -------------------------------------------------------------------------
    public static void afficherItineraire(Graph g, PathResult res, int source, int cible) {

        List<Integer> chemin = getPath(res.parent, cible);

        System.out.println("\n=== ITINÉRAIRE OPTIMAL ===");
        System.out.println("Chemin (sommets) : " +
                chemin.toString().replace(", ", " -> ").replace("[", "").replace("]", ""));

        double totalDistance = 0;
        List<String> ruesUtilisees = new ArrayList<>();

        System.out.println("\n=== DÉTAILS ===");

        for (int i = 0; i < chemin.size() - 1; i++) {
            int u = chemin.get(i);
            int v = chemin.get(i + 1);

            Edge e = g.getEdge(u, v);
            if (e == null) continue;

            totalDistance += e.weight;
            ruesUtilisees.add(e.streetName);

            double minutes = (e.weight / 1000.0) / 15.0 * 60.0; // 15 km/h

            System.out.printf("\n-> %s\n", e.streetName);
            System.out.println("  " + u + " -> " + v);
            System.out.println("  Distance : " + e.weight + " m");
            System.out.printf("  Temps estimé : %.1f minutes\n", minutes);
        }

        // Résumé
        double totalMinutes = (totalDistance / 1000.0) / 15.0 * 60.0;

        System.out.println("\n=== RÉSUMÉ ===");
        System.out.println("Rues empruntées : " + String.join(" → ", ruesUtilisees));
        System.out.println("Distance totale : " + totalDistance + " m");
        System.out.printf("Durée estimée : %.1f minutes\n", totalMinutes);

        // Rues non utilisées
        System.out.println("\n=== RUES NON UTILISÉES ===");

        Set<String> toutesRues = new HashSet<>();
        for (int u = 0; u < g.n; u++) {
            for (Edge e : g.getNeighbors(u)) {
                toutesRues.add(e.streetName);
            }
        }

        Set<String> utilisees = new HashSet<>(ruesUtilisees);

        List<String> nonUtilisees = new ArrayList<>();
        for (String r : toutesRues) {
            if (!utilisees.contains(r)) {
                nonUtilisees.add(r);
            }
        }

        if (nonUtilisees.isEmpty()) {
            System.out.println("Toutes les rues sont utilisées.");
        } else {
            System.out.println("Rues non sélectionnées : " + String.join(", ", nonUtilisees));
            System.out.println("\nRaisons possibles :");
            System.out.println(" - Elles ne font pas partie du plus court chemin.");
            System.out.println(" - Une autre route est plus rapide.");
            System.out.println(" - Dijkstra sélectionne uniquement les segments optimaux.");
        }
    }
}
