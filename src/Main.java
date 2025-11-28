import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== TEST DU CHARGEMENT DE GRAPHE ===");
        System.out.print("Nom du fichier à charger : ");

        String filename = sc.nextLine();

        Graph g = GraphLoader.loadGraph(filename);

        if (g != null) {
            System.out.println("Graphe chargé avec succès !");
            System.out.println(g);
        } else {
            System.out.println("Erreur lors du chargement.");
        }

        sc.close();
    }
}
