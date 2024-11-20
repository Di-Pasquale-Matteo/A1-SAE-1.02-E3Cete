/**
 * La classe Jeu permet de faire des parties du jeu "E3Cète" soit avec un humain, soit avec un ordinateur.
 *
 * Règles :
 *
 *  - On possède un paquet de cartes qui représentent entre une et trois figures (losange, carre ou ovale), une texture
 *   (vide, hachuré ou plein) et une couleur (rouge, jaune ou bleu). La cardinalité des énumérations est fixée à 3 pour cette partie 2 de la SAE uniquement.
 *
 *  - Une table 3x3 permet de stocker 9 cartes. Au début de la partie, on dispose 9 cartes sur la table, piochées sur le dessus du paquet.
 *
 *  - A chaque tour, le joueur doit essayer de trouver un E3C.
 *
 *  - Le joueur doit désigner trois cartes par leurs coordonnées.
 *      - Si ces cartes forment un E3C, il gagne trois points sur son score.
 *      - Si ce n'est pas un E3C, il perd 1 point sur son score.
 *
 *   - Les trois cartes sont remplacées par de nouvelles cartes piochées dans le paquet.
 *
 *   - La partie se termine quand il n'y a plus de cartes dans le paquet (même s'il reste des cartes sur la table).
 *
 * On a donc besoin :
 *
 * - D'un paquet pour stocker toutes les cartes et avoir une pioche.
 * - D'une table.
 * - De quoi stocker le score du joueur (humain ou ordinateur).
 */
public class Jeu {

    private int score;
    private Table table;
    private Paquet paquet;

    /**
     * Action :
     * - Initialise le jeu "E3Cète" en initialisant le score du joueur, le paquet et la table.
     * - La table doit être remplie.
     */

    public Jeu() {
        this.resetJeu();
    }

    /**
     * Action : Pioche autant de cartes qu'il y a de numéros de cartes dans le tableau numerosDeCartes et les place
     * aux positions correspondantes sur la table.
     */

    public void piocherEtPlacerNouvellesCartes(int[] numerosDeCartes) {
        for (int i = 0; i < numerosDeCartes.length; i++){
            this.table.setTableauCase((numerosDeCartes[i]-1)/3,(numerosDeCartes[i]-1)%3,this.paquet.piocher(1)[0]);
        }
    }

    /**
     * Action : Ré-initialise les données et variables du jeu afin de rejouer une nouvelle partie.
     */

    public void resetJeu() {
        this.score = 0;
        Couleur[] couleurs = {Couleur.ROUGE,Couleur.BLEU, Couleur.JAUNE};
        Figure[] figures = {Figure.OVALE, Figure.LOSANGE,Figure.CARRE};
        Texture[] textures = {Texture.PLEIN, Texture.VIDE, Texture.HACHURÉ};
        this.paquet = new Paquet(couleurs, 3, figures, textures);
        this.table = new Table(3,3);
        this.piocherEtPlacerNouvellesCartes(new int[] {1,2,3,4,5,6,7,8,9});
    }

    /**
     * Pre-requis : entiers.length == 3.
     * Résultat : Vrai si trois entiers du tableaux sont tous égaux ou 
     * tous différents, faux sinon.
     */

    public static boolean entiersEgauxOuDifferents(int[] entiers){
        if (entiers[0] == entiers[1] && entiers[0] == entiers[2]){
            return true;
        }
        if (entiers[0] != entiers[1] && entiers[0] != entiers[2] && entiers[1] != entiers[2]){
            return true;
        }
        return false;
    }

    /**
     * Résullat : Vrai si les cartes passées en paramètre forment un E3C.
     */

    public static boolean estUnE3C(Carte[] cartes) {
        int[] couleursOrdinaux = {cartes[0].getCouleur().ordinal(),
                cartes[1].getCouleur().ordinal(),cartes[2].getCouleur().ordinal()};
        int[] nbFigures = {cartes[0].getNbFigures(),cartes[1].getNbFigures(),cartes[2].getNbFigures()};
        int[] figuresOrdinaux = {cartes[0].getFigure().ordinal(),
                cartes[1].getFigure().ordinal(),cartes[2].getFigure().ordinal()};
        int[] texturesOrdinaux = {cartes[0].getTexture().ordinal(),
                cartes[1].getTexture().ordinal(),cartes[2].getTexture().ordinal()};
        
        if (entiersEgauxOuDifferents(couleursOrdinaux) && entiersEgauxOuDifferents(nbFigures)
                && entiersEgauxOuDifferents(figuresOrdinaux) && entiersEgauxOuDifferents(texturesOrdinaux)){
            return true;
        }
        return false;
    }

    /**
     * Action : Recherche un E3C parmi les cartes disposées sur la table.
     * Résullat :
     *  - Si un E3C existe, un tableau contenant les numéros de cartes (de la table) qui forment un E3C.
     *  - Sinon, la valeur null.
     */

    public int[] chercherE3CSurTableOrdinateur() {
        Carte[] cartes = new Carte[3];
        boolean trouve = false;
        int i = 0;
        int j = 1;
        int k = 2;
        while (i < 7 && !trouve){
            cartes[0] = this.table.getTableau()[i/3][i%3];
            j = i+1;
            while (j < 8 && !trouve){
                cartes[1] = this.table.getTableau()[j/3][j%3];
                k = j+1;
                while (k < 9 && !trouve){
                    cartes[2] = this.table.getTableau()[k/3][k%3];
                    trouve = estUnE3C(cartes);
                    k++;
                }
                j++;
            }
            i++;
        }
        if (trouve){
            int[]e3c = {i,j,k};
            return e3c;
        }
        return null;
    }

    /**
     * Action : Sélectionne alétoirement trois cartes sur la table.
     * La sélection ne doit pas contenir de doublons
     * Résullat : un tableau contenant les numéros des cartes sélectionnées alétaoirement
     */

    public int[] selectionAleatoireDeCartesOrdinateur() {
        int[] selection = new int[3];
        selection[0] = Ut.randomMinMax(1,9);
        int random = Ut.randomMinMax(1,9);
        while (random == selection[0]){
            random = Ut.randomMinMax(1,9);
        }
        selection[1] = random;
        while (random == selection[0] || random == selection[1]){
            random = Ut.randomMinMax(1,9);
        }
        selection[2] = random;
        return selection;
    }

    /**
     * Résullat : Vrai si la partie en cours est terminée.
     */

    public boolean partieEstTerminee() {
        return (this.paquet.getNbCartes() == 0);
    }

    /**
     * Action : Fait jouer un tour à un joueur humain.
     * La Table et le score du joueur sont affichés.
     * Le joueur sélectionne 3 cartes.
     *  - Si c'est un E3C, il gagne trois points.
     *  - Sinon, il perd un point.
     * Les cartes sélectionnées sont remplacées.
     * Divers messages d'informations doivent être affichés pour l'ergonomie.
     */

    public void jouerTourHumain() {
        System.out.println("Cherchez un E3C !");
        System.out.println("Score : " + this.score);
        for (int i = 1; i <= 9; i+=3){
            int[] triplet = {i,i+1,i+2};
            this.table.afficherSelection(triplet);
        }
        int[] jouer = this.table.selectionnerCartesJoueur(3);
        Carte[] jouerCarte = {this.table.getTableau()[(jouer[0]-1)/3][(jouer[0]-1)%3],
                     this.table.getTableau()[(jouer[1]-1)/3][(jouer[1]-1)%3],
                     this.table.getTableau()[(jouer[2]-1)/3][(jouer[2]-1)%3]};
        if(estUnE3C(jouerCarte)){
            this.score += 3;
            System.out.println("C'est bien un E3C, vous gagnez 3 points!");
        }
        else{
            this.score--;
            System.out.println("Ce n'était pas un E3C, vous perdez 1 point!");
        }
        this.piocherEtPlacerNouvellesCartes(jouer);
    }

    /**
     * Action : Fait jouer une partie à un joueur humain.
     * A la fin, le score final du joueur est affiché.
     */

    public void jouerHumain() {
        while (!this.partieEstTerminee()){
            this.jouerTourHumain();
        }
        System.out.println("Score final : " + this.score);
    }

    /**
     * Action : Fait jouer un tour à l'ordinateur.
     * La Table et le score de l'ordinateur sont affichés.
     * L'ordinateur sélectionne des cartes :
     *  - L'ordinateur essaye toujours de trouver un E3C sur la table. S'il en trouve un, il gagne donc trois points.
     *  - S'il n'en trouve pas, il se rabat sur 3 cartes sélectionnées aléatoirement et perd un point.
     * Les cartes sélectionnées sont remplacées.
     * Divers messages d'informations doivent être affichés pour l'ergonomie.
     */

    public void jouerTourOrdinateur() {
        System.out.println("Cherchez un E3C !");
        System.out.println("Score : " + this.score);
        for (int i = 1; i <= 9; i+=3){
            int[] triplet = {i,i+1,i+2};
            this.table.afficherSelection(triplet);
        }
        int[] e3c = this.chercherE3CSurTableOrdinateur();
        if (e3c == null){
            e3c = this.selectionAleatoireDeCartesOrdinateur();
        }
        System.out.println("Cartes sélectionnées : "+e3c[0]+" "+e3c[1]+" "+e3c[2]);
        Carte[] e3cCartes = {this.table.getTableau()[(e3c[0]-1)/3][(e3c[0]-1)%3],
                     this.table.getTableau()[(e3c[1]-1)/3][(e3c[1]-1)%3],
                     this.table.getTableau()[(e3c[2]-1)/3][(e3c[2]-1)%3]};
        if(estUnE3C(e3cCartes)){
            this.score += 3;
            System.out.println("C'est bien un E3C, l'ordinateur gagne 3 points!");
        }
        else{
            this.score--;
            System.out.println("Ce n'était pas un E3C, l'ordinateur perd 1 point!");
        }
        this.piocherEtPlacerNouvellesCartes(e3c);
    }
    

    /**
     * Action : Fait jouer une partie à l'ordinateur.
     * Une pause est faite entre chaque tour (500 ms ou plus) afin de pouvoir observer la progression de l'ordinateur.
     * A la fin, le score final de l'ordinateur est affiché.
     * Rappel : Ut.pause(temps) permet de faire une pause de "temps" millisecondes
     */

    public void jouerOrdinateur() {
        while (!this.partieEstTerminee()){
            this.jouerTourOrdinateur();
            Ut.pause(1000);
        }
        System.out.println("Score final : " + this.score);
    }

    /**
     * Action : Permet de lancer des parties de "E3Cète" au travers d'un menu.
     * Le menu permet au joueur de sélectionner une option parmi :
     *  - humain : lance une partie avec un joueur humain
     *  - ordinateur : lance une partie avec un ordinateur
     *  - terminer : arrête le programme.
     * Après la fin de chaque partie, les données de jeu sont ré-initialisées et le menu est ré-affiché
     * (afin de faire une nouvelle sélection).
     * Les erreurs de saisie doivent être gérées (si l'utilisateur sélectionne une option inexistante).
     * Divers messages d'informations doivent être affichés pour l'ergonomie.
     */

    public void jouer() {
        String reponse = "";
        while (!"terminer".equals(reponse)){
            System.out.println("Voulez vous jouer (tapez humain), faire jouer un ordinateur (ordinateur) ou arreter de jouer (terminer) ?");
            reponse = Ut.saisirChaine();
            while (!"humain".equals(reponse) && !"ordinateur".equals(reponse) && !"terminer".equals(reponse)){
                System.out.println("Retapez votre réponse (humain/ordinateur/terminer).");
                reponse = Ut.saisirChaine();
            }
            if ("humain".equals(reponse)) {
                this.jouerHumain();
            }
            else if ("ordinateur".equals(reponse)){
                this.jouerOrdinateur();
            }
            this.resetJeu();
        }
    }

    public float proba3CR(int nombreEssais) {
        int succes = 0;
        for (int i = 0; i < nombreEssais; i++) {
            int cartesRouges = 0;
            for (int j = 1; j <= 9; j++) { 
                if (this.table.getTableau()[(j-1)/3][(j-1)%3].getCouleur().ordinal() == 0) { 
                    cartesRouges++;
                }
            }
            if (cartesRouges == 3) {
                succes++;
            }
            this.resetJeu();
        }
        return (float) succes / nombreEssais;
    }

    public float probaE3C(int nombreEssais) {
        int succes = 0;
        for (int i = 0; i < nombreEssais; i++) {
            if (this.chercherE3CSurTableOrdinateur() != null) {
                succes++;
            }
            this.resetJeu();
        }
        return (float) succes / nombreEssais;
    }
}
