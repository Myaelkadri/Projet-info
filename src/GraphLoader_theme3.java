import java.io.File;
import java.util.Scanner;

public class GraphLoader_theme3 {

    public static class Theme3Data {
        public Graph graph;         // le graphe des secteurs
        public int[] quantities;    // les quantités de déchets par secteur

        public Theme3Data(Graph g, int[] q) {
            this.graph = g;
            this.quantities = q;
        }
    }

    public static Theme3Data load(String filename) {

        Graph g = null;
        int[] quantities = null;

        try {
            File file = new File(filename);
            Scanner sc = new Scanner(file);

            // ---------- LECTURE NB SECTEURS ----------
            int n = sc.nextInt();
            g = new Graph(n);

            // ---------- LECTURE QUANTITÉS ----------
            quantities = new int[n];
            for (int i = 0; i < n; i++) {
                quantities[i] = sc.nextInt();
            }

            // ---------- LECTURE DES VOISINAGES ----------
            while (sc.hasNext()) {
                int u = sc.nextInt();
                int v = sc.nextInt();

                // Pour le thème 3, pas de poids → on met 1 ou 0
                g.addEdge(u, v, 1, "");
            }

            sc.close();

        } catch (Exception e) {
            System.out.println("Erreur lors du chargement du fichier : " + filename);
            e.printStackTrace();
        }

        return new Theme3Data(g, quantities);
    }
}
