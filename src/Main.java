import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== TEST DE CHARGEMENT DE GRAPHE ===");

        // 1) Nom du fichier texte
        System.out.print("Nom du fichier à charger : ");
        String filename = sc.nextLine();

        // 2) Choix du thème
        System.out.print("Quel thème ? (1 / 2 / 3) : ");
        int theme = sc.nextInt();
        sc.nextLine(); // nettoyer buffer

        // ============================================================
        // ======================== THEME 1 & 2 ========================
        // ============================================================
        if (theme == 1 || theme == 2) {

            Graph g = GraphLoader_theme1_2.loadGraph(filename);

            if (g == null) {
                System.out.println("Erreur lors du chargement.");
                return;
            }

            System.out.println("\nGraphe chargé (Thème 1/2) :");
            System.out.println(g);
            sc.close();
            return;
        }

        // ============================================================
        // ======================== THEME 3 ============================
        // ============================================================
        else if (theme == 3) {

            GraphLoader_theme3.Theme3Data data = GraphLoader_theme3.load(filename);
            Graph g = data.graph;

            if (g == null) {
                System.out.println("Erreur lors du chargement du graphe.");
                return;
            }

            System.out.println("\nGraphe chargé (Thème 3) :");

            // Choix de l'hypothèse
            System.out.print("\nChoisir une hypothèse (1 ou 2) : ");
            int hypo = sc.nextInt();
            sc.nextLine();

            // ============================================================
            //                 HYPOTHÈSE 1 : COLORATION SIMPLE
            // ============================================================
            if (hypo == 1) {

                System.out.println("\n=== Coloration naïve ===");
                int[] naive = AlgorithmsTheme3.colorNaive(g);

                for (int i = 0; i < naive.length; i++) {
                    System.out.println("Secteur " + i + " → Jour " + naive[i]);
                }

                System.out.println("\n=== Welsh et Powell ===");
                int[] wp = AlgorithmsTheme3.welshPowell(g);

                for (int i = 0; i < wp.length; i++) {
                    System.out.println("Secteur " + i + " → Jour " + wp[i]);
                }

                sc.close();
                return;
            }

            // ============================================================
            //            HYPOTHÈSE 2 : AVEC CONTRAINTE DE CAPACITÉ
            // ============================================================
            else if (hypo == 2) {
                System.out.println("\nQuantités :");
                for (int q : data.quantities) System.out.print(q + " ");
                System.out.println();

                System.out.print("\nCapacité du camion ? : ");
                int C = sc.nextInt();
                sc.nextLine();

                // 1) Coloration sans contrainte : Welsh & Powell
                System.out.println("\n=== Welsh et Powell ===");
                int[] colors = AlgorithmsTheme3.welshPowell(g);


                for (int i = 0; i < colors.length; i++) {
                    System.out.println("Secteur " + i + " → Jour " + colors[i]);
                }

                // 2) Planification avec la contrainte
                List<List<Integer>> jours =
                        AlgorithmsTheme3.planificationAvecCapacite(g, data.quantities, C, colors);

                System.out.println("\n=== Planification avec contrainte ===");

                for (int i = 0; i < jours.size(); i++) {

                    List<Integer> jour = jours.get(i);

                    // Calcul quantité totale du jour
                    int total = 0;
                    for (int secteur : jour) {
                        total += data.quantities[secteur];
                    }

                    System.out.println(
                            "Jour " + (i + 1) + " : " + jour +
                                    "  → Quantité totale = " + total);
                }

                sc.close();
                return;
            }

            else {
                System.out.println("Hypothèse invalide.");
            }
        }

        else {
            System.out.println("Thème invalide.");
        }

        sc.close();
    }
}
