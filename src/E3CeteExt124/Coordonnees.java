public class Coordonnees {


/**
 * La classe Coordonnees représente les coordonnées (i,j) d'une Carte sur la Table
 * ou i représenta la ligne et j la colonne
 * Cette classe est utilisée uniquement pour intéragir avec l'utilisateur
 *  */


    private int ligne;
    private int colonne;

    /**
     * Pre-requis : x, y >= 0
    * Action : Construit des Coordonnées ayant x comme numéro de ligne et y comme numéro de colonne.
    */
    public Coordonnees(int x, int y) {
        this.ligne = x;
        this.colonne = y;
    }

    /**
     * Pre-requis : input est sous la forme  suivante : int,int
     * Action : Construit des Coordonnées ayant x comme numéro de ligne et y comme numéro de colonne
     */
    public Coordonnees(String input) {
        String[] splited = input.split(",");
        this.ligne = splited[0].toUpperCase().charAt(0) - 'A';
        this.colonne = Integer.parseInt(splited[1]) - 1;
    }

    /**
     * Action : Retourne le numéro de la ligne
     */
    public int getLigne() {
        return this.ligne;
    }

    /**
     * Action : Retourne le numéro de la colonne
     */
    public int getColonne() {
        return this.colonne;
    }

    /**
     * Pre-requis : aucun
     * Action : Retourne vrai si la variable input est dans un format valide à savoir int,int
     * Aide : On peut utiliser Ut.estNombre pour vérifier qu'une chaîne de caractères est bien un nombre.
     */
    public static boolean formatEstValide(String input) {
        String[] parts = input.split(",");
        if (parts.length != 2) {
            return false;
        }
        return parts[0].length() == 1 && parts[0].toUpperCase().matches("[A-T]") &&
               Ut.estNombre(parts[1]) && Integer.parseInt(parts[1]) >= 1 && Integer.parseInt(parts[1]) <= 20;
    }
}
