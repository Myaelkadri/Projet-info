import java.util.ArrayList;
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

        if (theme == 1 ) {

            Graph g = GraphLoader_theme1_2.loadGraph(filename);

            if (g == null) {
                System.out.println("Erreur chargement graphe");
                return;
            }

            // Choix de la prob
            System.out.print("\nChoisir une problematique (1 ou 2) : ");
            int prob = sc.nextInt();
            sc.nextLine();

            if (prob == 1){
                System.out.print("\nChoisir une hypothese (1 ou 2) : ");
                int theme1prob1 = sc.nextInt();
                sc.nextLine();

                if (theme1prob1 == 1){
                    System.out.print("\n=== Theme 1 Problematique 1  hypothese 1 ===");

                    System.out.print("\nSource : ");
                    int source = sc.nextInt();

                    System.out.print("Destination : ");
                    int dest = sc.nextInt();

                    AlgorithmsTheme1Prob1.PathResult res = AlgorithmsTheme1Prob1.dijkstra(g, source);

                    System.out.println("Distance totale = " + res.dist[dest]);

                    List<Integer> path = AlgorithmsTheme1Prob1.getPath(res.parent, dest);
                    List<Edge> steps = AlgorithmsTheme1Prob1.getPathEdges(g, res.parent, dest);

                    System.out.println("\n=== Itinéraire détaillé ===");

                    for (Edge e : steps) {
                        System.out.println(
                                "Prendre " + e.streetName +
                                        " ( " + e.weight + " m ) ");
                    }

                    System.out.println("\nDistance totale : " + res.dist[dest] + " m");


                }
                else if( theme1prob1 == 2){
                    System.out.print("\n=== Theme 1 Problematique 1  hypothese 2 ===");


                    System.out.print("\n Point de départ : ");
                    int start = sc.nextInt();

                    System.out.println("Entrer les maisons à visiter (terminer par -1) :");

                    List<Integer> houses = new ArrayList<>();
                    while (true) {
                        int h = sc.nextInt();
                        if (h == -1) break;
                        houses.add(h);
                    }

                    // on s'assure que le dépôt est dans la liste
                    if (!houses.contains(start)) {
                        houses.add(0, start);
                    }

                    // calcul de la tournée
                    List<Integer> tour = AlgorithmsTheme1Prob1.nearestNeighbor(g, start, houses);

                    System.out.println("\n=== Tournée trouvée ===");
                    System.out.println(tour);

                    // affichage détaillé style Google Maps
                    AlgorithmsTheme1Prob1.printTourDetails(g, tour);
                }


            }
            else if(prob == 2){
                System.out.print("\nChoisir une cas (1 ou 2 ou 3) : ");
                int theme1prob2 = sc.nextInt();
                sc.nextLine();

                if (theme1prob2 == 1){
                    System.out.print("\n=== Theme 1 Problematique 2  Cas 1 ===");


                    if (AlgorithmsTheme1Prob2.isEulerian(g)) {
                        System.out.println("\nLe graphe est Eulérien (tous les sommets pairs).");

                        List<Integer> cycle = AlgorithmsTheme1Prob2.eulerianCycle(g);
                        AlgorithmsTheme1Prob2.afficherTournee(g, cycle);

                    } else {
                        System.out.println("\nLe graphe N'est PAS eulérien → sommets impairs présents !");
                        System.out.println("Passer au Cas 2 (2 sommets impairs) ou Cas 3 (CPP).");
                    }
                }

                else if(theme1prob2 == 2){
                    System.out.print("\n=== Theme 1 Problematique 2  Cas 2 ===");

                        List<Integer> odd = AlgorithmsTheme1Prob2.findOddVertices(g);

                        if (odd.size() != 2) {
                            System.out.println("Impossible : le graphe n'a pas exactement 2 sommets impairs.");
                        } else {
                            List<Integer> tournee = AlgorithmsTheme1Prob2.cas2_tournee(g);
                            AlgorithmsTheme1Prob2.afficherTourneeDetaillee(g, tournee);
                        }

                }
                else if( theme1prob2 == 3){
                    System.out.print("\n=== Theme 1 Problematique 2  Cas 3 ===");
                    List<Integer> tour = AlgorithmsTheme1Prob2.cas3_CPP(g);
                    AlgorithmsTheme1Prob2.afficher(g, tour);

                }
            }
        }

        else if (theme ==2){

            System.out.print("\nChoisir une Approche (1 ou 2) : ");
            int approche = sc.nextInt();
            sc.nextLine();

            if (approche == 1){
                // Approche 1
            }else if (approche == 2){
                Graph g = GraphLoader_theme1_2.loadGraph(filename);

                List<Integer> route = AlgorithmsTheme2.computeMSTRoute(g);
                AlgorithmsTheme2.afficherTourneeMST(g, route);

            }

        }
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
