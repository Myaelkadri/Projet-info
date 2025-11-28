import java.io.*;
import java.util.*;

public class GraphLoader {

    public static Graph loadGraph(String filename) {
        Graph g = null;

        try {
            File file = new File(filename);
            Scanner sc = new Scanner(file);

            // ---------- Lire le nombre de sommets ----------
            int n = sc.nextInt();
            g = new Graph(n);

            // Lire chaque ligne (u v distance nom_de_rue)
            while (sc.hasNext()) {

                int u = sc.nextInt();
                int v = sc.nextInt();
                double w = sc.nextDouble();

                String street = sc.next(); // nom_de_rue (sans espaces)

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
