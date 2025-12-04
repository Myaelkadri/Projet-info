import java.io.File;
import java.util.Scanner;

public class GraphLoader_theme1_2 {

    public static Graph loadGraph(String filename) {
        Graph g = null;

        try {
            File file = new File(filename);
            Scanner sc = new Scanner(file);

            // --------- Lire le nombre de sommets ----------
            int n = sc.nextInt();
            g = new Graph(n);

            // Lire chaque ligne : u v distance nom_de_rue
            while (sc.hasNext()) {

                int u = sc.nextInt();
                int v = sc.nextInt();
                int w = sc.nextInt();

                String street = sc.next(); // nom de rue (si espaces → remplacer par '_')

                g.addEdge(u, v, w, street);
            }

            sc.close();

        } catch (Exception e) {
            System.out.println("Erreur lors du chargement du fichier : " + filename);
            e.printStackTrace();
        }

        return g;
    }
}
