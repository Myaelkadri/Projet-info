import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlgorithmsTheme3 {

     // Naive
    public static int[] colorNaive(Graph g) {
        int n = g.getNbVertices();
        int[] color = new int[n];

        // -1 = pas encore colorié
        Arrays.fill(color, -1);

        // pour chaque sommet
        for (int u = 0; u < n; u++) {

            // couleurs déjà utilisées par les voisins
            boolean[] used = new boolean[n];

            for (Edge e : g.getNeighbors(u)) {
                int v = e.to;
                if (color[v] != -1) {
                    used[color[v]] = true;
                }
            }

            // attribuer la 1ère couleur non utilisée
            int c = 0;
            while (c < n && used[c]) c++;

            color[u] = c;
        }

        return color;
    }


    //  WELSH & POWELL
    public static int[] welshPowell(Graph g) {

        int n = g.getNbVertices();
        int[] color = new int[n];

        Arrays.fill(color, -1); // pas colorié

        // 1) On crée une liste des sommets
        ArrayList<Integer> vertices = new ArrayList<>();
        for (int i = 0; i < n; i++)
            vertices.add(i);

        // 2) Trier selon le degré décroissant
        vertices.sort((a, b) -> g.getNeighbors(b).size() - g.getNeighbors(a).size());

        int currentColor = 0;

        // 3) Welsh & Powell : colorier dans cet ordre
        for (int u : vertices) {

            if (color[u] == -1) { // pas encore colorié
                color[u] = currentColor;

                // Colorer les sommets compatibles (pas voisins)
                for (int v : vertices) {
                    if (color[v] == -1 && !areAdjacent(g, u, v, color, currentColor)) {
                        color[v] = currentColor;
                    }
                }

                // passer à la couleur suivante
                currentColor++;
            }
        }

        return color;
    }

    // On vérifie si v ne peut pas prendre la couleur actuelle
    private static boolean areAdjacent(Graph g, int u, int v, int[] color, int colorToAssign) {

        // Vérifier si u et v sont voisins
        for (Edge e : g.getNeighbors(u)) {
            if (e.to == v) return true;
        }

        // Vérifier si v a un voisin déjà colorié avec cette couleur
        for (Edge e : g.getNeighbors(v)) {
            if (color[e.to] == colorToAssign) return true;
        }

        return false;
    }

    // La contrainte
    public static List<List<Integer>> planificationAvecCapacite(Graph g, int[] quantities, int capacite, int[] colors) {

        int maxColor = 0;
        for (int c : colors) maxColor = Math.max(maxColor, c);

        // Résultat final : liste des jours
        List<List<Integer>> jours = new ArrayList<>();

        // Pour chaque couleur
        for (int c = 0; c <= maxColor; c++) {

            // Récupérer les secteurs de cette couleur
            List<Integer> secteurs = new ArrayList<>();
            for (int i = 0; i < colors.length; i++) {
                if (colors[i] == c) secteurs.add(i);
            }

            // Remplissage en respectant la capacité
            int somme = 0;
            List<Integer> jourActuel = new ArrayList<>();

            for (int s : secteurs) {

                int q = quantities[s];

                // Si ajouter ce secteur dépasse la capacité → créer un nouveau jour
                if (somme + q > capacite) {
                    jours.add(jourActuel);       // on ferme le jour
                    jourActuel = new ArrayList<>();
                    somme = 0;
                }

                jourActuel.add(s);
                somme += q;
            }

            // Ajouter le dernier jour si non vide
            if (!jourActuel.isEmpty()) {
                jours.add(jourActuel);
            }
        }

        return jours;
    }

    public static void afficherColorationNaive(Graph g, int[] colors) {
        System.out.println("\n=== Coloration naïve ===");
        for (int i = 0; i < colors.length; i++) {
            System.out.println(g.indexToName[i] + " → Jour " + colors[i]);
        }
    }

    public static void afficherColorationWelshPowell(Graph g, int[] colors) {
        System.out.println("\n=== Coloration Welsh & Powell ===");
        for (int i = 0; i < colors.length; i++) {
            System.out.println(g.indexToName[i] + " → Jour " + colors[i]);
        }
    }

}
