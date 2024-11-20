/**
 * La classe Table représente une table de jeu contenant des cartes.
 *
 * La table est représentée graphiquement par une matrice.
 * On peut donc avoir des tables de dimensions 3x3, 3x4, 4x4, 5x5, 10x15...
 * En mémoire, la Table est représentée par un simple tableau (à une dimension)
 * Quand elle est initialisée, la table est vide.
 *
 * Pour désigner une carte sur la table, on utilise des coordonées (i,j) ou i représenta la ligne et j la colonne.
 * Les lignes et colonnes sont numérotés à partir de 1.
 * Les cartes sont numérotées à partir de 1.
 *
 * Par exemple, sur une table 3x3, la carte en position (1,1) est la premiere carte du tableau, soit celle à l'indice 0.
 * La carte (2,1) => carte numéro 4 stockée à l'indice 3  dans le tableau représenatnt la table
 * La carte (3,3) => carte numéro 9 stockée à l'indice 8  dans le tableau représenatnt la table
 */
public class Table {
    private int hauteur;
    private int largeur;
    private Carte[][] tableau;

    /**
     * Pre-requis : hauteur >=3, largeur >=3
     *
     * Action : Construit une table "vide" avec les dimensions précisées en paramètre.
     *
     * Exemple : hauteur : 3, largeur : 3 => construit une table 3x3 (pouvant donc accueillir 9 cartes).
     */

    public Table(int hauteur, int largeur){
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.tableau = new Carte[hauteur][largeur];
    }

    /**
     * Résullat : Le nombre de cartes que la table peut stocker.
     */

    public int getTaille() {
        return hauteur * largeur;
    }

    public Carte[][] getTableau(){
        return this.tableau;
    }  

    public void setTableauCase(int x, int y, Carte carte){
        this.tableau[x][y] = carte;
    }

    public int getHauteur(){
        return this.hauteur;
    }

    public int getLargeur(){
        return this.largeur;
    }

    /**
     * Pre-requis : la table est pleine
     * Action : Affiche des cartes de la table sous forme de matrice
     * L'affichage des cartes doit respecter le format défini dans la classe Carte (chaque carte doit donc être colorée).
     * On ne donne volontairement pas d'exemple puisque celà depend du choix fait pour votre représentation de Carte
     */

    public String toString() {
        String resultat = "   ";
        for (int j = 1; j <= largeur; j++) {
            resultat += j + " ";
        }
        resultat += "\n";
    
        for (int i = 0; i < hauteur; i++) {
            resultat += (char)('A' + i) + " ";
            for (int j = 0; j < largeur; j++) {
                if (tableau[i][j] != null) {
                    resultat += tableau[i][j] + " ";
                } else {
                    resultat += "[Vide] ";
                }
            }
            resultat += "\n";
        }
        return resultat;
    }
    

    /**
     * Résullat : Vrai la carte située aux coordonnées précisées en paramètre est une carte possible pour la table.
     */
    public boolean carteExiste(Coordonnees coordonnees) {
        int ligne = coordonnees.getLigne();
        int colonne = coordonnees.getColonne();
        return ligne >= 0 && ligne < this.hauteur && colonne >= 0 && colonne < this.largeur && this.tableau[ligne][colonne]!=null;
    }

    /**
     * Pre-requis :
     *  Il reste des cartes sur la table.
     *
     * Action : Fait sélectionner au joueur (par saisie de ses coordonnées) une carte valide (existante) de la table.
     * L'algorithme doit faire recommencer la saisie au joueur s'il ne saisit pas une carte valide.
     *
     * Résullat : Le numéro de carte sélectionné.
     *
     */

    public int faireSelectionneUneCarte() {
        int numeroCarte = -1;
    
        while (numeroCarte == -1) {
            System.out.println("Entrez les coordonnées (ex. A,1):");
            String input = Ut.saisirChaine();
            if (Coordonnees.formatEstValide(input)) {
                Coordonnees coord = new Coordonnees(input);
                if (carteExiste(coord)) {
                    numeroCarte = coord.getLigne() * largeur + coord.getColonne() + 1;
                } else {
                    Ut.afficherSL("Coordonnées invalides. Veuillez saisir des coordonnées valides.");
                }
            } else {
                Ut.afficherSL("Format invalide. Veuillez entrer les coordonnées au format Lettre,Chiffre.");
            }
        }
        
        return numeroCarte;
    }
    
    

    /**
     * Pre-requis : 1<=nbCartes <= nombre de Cartes de this
     * Action : Fait sélectionner nbCartes Cartes au joueur  sur la table en le faisant recommencer jusqu'à avoir une sélection valide.
     * Il ne doit pas y avoir de doublons dans les numéros de cartes sélectionnées.
     * Résullat : Un tableau contenant les numéros de cartes sélectionnées.
     */

    public int[] selectionnerCartesJoueur(int nbCartes) {
        int[] selection = new int[nbCartes];
        int cartesSelectionnees = 0;
    
        while (cartesSelectionnees < nbCartes) {
            int numeroCarte = faireSelectionneUneCarte();
            boolean estDejaSelectionnee = false;

            for (int j = 0; j < cartesSelectionnees; j++) {
                if (selection[j] == numeroCarte) {
                    estDejaSelectionnee = true;
                    Ut.afficherSL("Cette carte a déjà été sélectionnée. Veuillez en choisir une autre.");
                }
            }

            if (!estDejaSelectionnee) {
                selection[cartesSelectionnees] = numeroCarte;
                cartesSelectionnees++;
            }
        }
    
        return selection;
    }
    
    

    /**
     * Action : Affiche les cartes de la table correspondantes aux numéros de cartes contenus dans selection
     * Exemple de format d'affichage : "Sélection : 2-O-H 3-O-H 2-C-H"
     * Les cartes doivent être affichées "colorées"
     */

     public void afficherSelection(int[] selection) {
        String resultat = " ";
    
        for (int i = 0; i < selection.length; i++) {
            int numeroCarte = selection[i];
            int ligne = (numeroCarte - 1) / largeur;
            int colonne = (numeroCarte - 1) % largeur;
            char ligneLettre = (char)('A' + ligne);
            int colonneChiffre = colonne + 1;
    
            if (tableau[ligne][colonne] != null) {
                resultat += ligneLettre + "," + colonneChiffre + " : " + tableau[ligne][colonne].toString() + "     ";
            } else {
                resultat += ligneLettre + "," + colonneChiffre + " : [Vide]     ";
            }
        }
        Ut.afficherSL(resultat);
    }
}
