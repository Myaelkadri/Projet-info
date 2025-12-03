public class AlgorithmsTheme1Prob1 {

    import java.util.*;

    public static class PathResult {
        public int[] dist;
        public int[] parent;

        public PathResult(int[] dist, int[] parent) {
            this.dist = dist;
            this.parent = parent;
        }
    }

    public static PathResult dijkstra(Graph g, int source) {
        int n = g.n;
        int[] dist = new int[n];
        int[] parent = new int[n];
        boolean[] visited = new boolean[n];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        dist[source] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.add(new int[]{source, 0});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int u = curr[0];

            if (visited[u]) continue;
            visited[u] = true;

            for (Edge e : g.getNeighbors(u)) {
                int v = e.to;
                int w = (int) e.weight;

                if (dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    parent[v] = u;
                    pq.add(new int[]{v, dist[v]});
                }
            }
        }

        return new PathResult(dist, parent);
    }

    public static List<Integer> getPath(int[] parent, int dest) {
        List<Integer> path = new ArrayList<>();
        int curr = dest;

        while (curr != -1) {
            path.add(curr);
            curr = parent[curr];
        }

        Collections.reverse(path);
        return path;
    }

    public static List<Edge> getPathEdges(Graph g, int[] parent, int dest) {
        List<Edge> edges = new ArrayList<>();
        int curr = dest;

        while (parent[curr] != -1) {
            int from = parent[curr];
            int to = curr;

            for (Edge e : g.getNeighbors(from)) {
                if (e.to == to) {
                    edges.add(e);
                    break;
                }
            }
            curr = from;
        }

        Collections.reverse(edges);
        return edges;
    }

    public static void printTourDetails(Graph g, List<Integer> tour) {
        System.out.println("\n=== Itinéraire détaillé ===");

        double total = 0;

        for (int i = 0; i < tour.size() - 1; i++) {
            int u = tour.get(i);
            int v = tour.get(i + 1);

            PathResult pr = dijkstra(g, u);
            List<Edge> edges = getPathEdges(g, pr.parent, v);

            int from = u;

            for (Edge e : edges) {
                System.out.println(from + " -> " + e.to + " : " + e.streetName + " (" + e.weight + " m)");
                total += e.weight;
                from = e.to;
            }
        }

        System.out.println("\nDistance totale = " + total + " m");
    }
}
