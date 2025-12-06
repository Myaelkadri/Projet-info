import java.io.File;
import java.util.Scanner;

public class GraphLoader_theme3 {

    public static class Theme3Data {
        public Graph graph;          // graphe des conflits
        public int[] quantities;     // quantités de déchets
        public String[] names;       // noms des secteurs

        public Theme3Data(Graph g, int[] q, String[] n) {
            this.graph = g;
            this.quantities = q;
            this.names = n;
        }
    }

    public static Theme3Data load(String filename) {

        Graph g = null;
        int[] quantities = null;
        String[] names = null;

        try {
            File file = new File(filename);
            Scanner sc = new Scanner(file);

            // 1. Nombre de secteurs
            int n = sc.nextInt();
            g = new Graph(n);

            // 2. Quantités
            quantities = new int[n];
            for (int i = 0; i < n; i++) {
                quantities[i] = sc.nextInt();
            }

            sc.nextLine(); // vider la ligne

            // 3. Noms des secteurs
            names = new String[n];
            for (int i = 0; i < n; i++) {
                names[i] = sc.nextLine().trim();
            }

            // Stocker les noms dans le Graph
            g.indexToName = names;

            // 4. Arêtes (conflits)
            while (sc.hasNext()) {
                int u = sc.nextInt();
                int v = sc.nextInt();

                // graphe non orienté
                g.addEdge(u, v, 1, "");
                g.addEdge(v, u, 1, "");
            }

            sc.close();

        } catch (Exception e) {
            System.out.println("Erreur lors du chargement du fichier : " + filename);
            e.printStackTrace();
        }

        return new Theme3Data(g, quantities, names);
    }
}
