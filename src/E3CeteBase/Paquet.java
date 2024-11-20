/**
 * La classe Paquet représente un paquet de cartes.
 * Les cartes sont stockées dans un tableau fixe et un indice (entier) permet de connaître le nombre de cartes
 * restantes (non piochées) dans le paquet. Quand on pioche, cet indice diminue.
 * Dans les traitements, on considère alors seulement les cartes se trouvant entre 0 et cet indice (exclus).
 * Par conséquent, on ne supprime pas vraiment les cartes piochées, on les ignore juste.
 * On a donc besoin de connaître :
 * - Le tableau stockant les cartes.
 * - Le nombre de cartes restantes dans le paquet.
 */
public class Paquet {


    private Carte[] cartes;
    private int nbCartes;

    /**
     * Pre-requis : figures.length > 0, couleurs.length > 0, textures.length > 0, nbFiguresMax > 0
     *
     * Action : Construit un paquet de cartes mélangé contenant toutes les cartes incluant 1 à nbFiguresMax figures
     * qu'il est possibles de créer en combinant les différentes figures, couleurs et textures précisées en paramètre.
     * Bien sûr, il n'y a pas de doublons.
     *
     * Exemple :
     *  - couleurs = [Rouge, Jaune]
     *  - nbFiguresMax = 2
     *  - figures = [A, B]
     *  - textures = [S, T]
     *  Génère un paquet (mélangé) avec toutes les combinaisons de cartes possibles pour ces caractéristiques : 1-A-S (rouge), 1-A-T (rouge), etc...
     */

    public Paquet(Couleur[] couleurs, int nbFiguresMax, Figure[] figures, Texture[] textures) {
        int nombreCartes = getNombreCartesAGenerer(couleurs, nbFiguresMax, figures, textures);
        this.cartes = new Carte[nombreCartes];
        int x = 0;
        for (int i = 0; i < couleurs.length; i++) {
            for (int j = 1; j <= nbFiguresMax; j++) {
                for (int k = 0; k < figures.length; k++) {
                    for (int l = 0; l < textures.length; l++) {
                        this.cartes[x++] = new Carte(couleurs[i], j, figures[k], textures[l]);
                    }
                }
            }
        }
        this.nbCartes = nombreCartes;
        this.melanger();
    }
    
    /**
     * Action : Construit un paquet par recopie en copiant les données du paquet passé en paramètre.
     */

    public Paquet(Paquet paquet) {
        this.cartes = new Carte[paquet.cartes.length];
        for (int i = 0; i < paquet.cartes.length; i++){
            this.cartes[i] = new Carte(paquet.cartes[i]);
        }
        this.nbCartes = paquet.nbCartes;
    }


    /**
     * Pre-requis : figures.length > 0, couleurs.length > 0, textures.length > 0, nbFiguresMax > 0
     *
     * Resultat : Le nombre de cartes uniques contenant entre 1 et nbFiguresMax figures qu'il est possible de générer en
     * combinant les différentes figures, couleurs et textures précisées en paramètre.
     */

    public static int getNombreCartesAGenerer(Couleur[] couleurs, int nbFiguresMax, Figure[] figures, Texture[] textures) {
        int nombre = couleurs.length * nbFiguresMax * figures.length * textures.length;
        return nombre;
    }   

    public int getNbCartes(){
        return this.nbCartes;
    }

    /**
     * Action : Mélange aléatoirement les cartes restantes dans le paquet.
     * Attention, on rappelle que le paquet peut aussi contenir des cartes déjà piochées qu'il faut ignorer.
     */

    public void melanger() {
        for (int i = this.nbCartes - 1; i > 0; i--) {
            int ran = Ut.randomMinMax(0, i);
            Carte temp = this.cartes[i];
            this.cartes[i] = this.cartes[ran];
            this.cartes[ran] = temp;
        }
    }

    


    /**
     * Action : Calcule et renvoie un paquet trié à partir du paquet courant (this) selon la méthode du tri selection.
     * Le tri est effectué à partir des données du paquet courant (this) mais celui-ci ne doit pas être modifié !
     * Une nouvelle instance du paquet est traitée et renvoyée.
     * On rappelle que le paquet peut aussi contenir des cartes déjà piochées  qu'il faut ignorer (voir partie 2 de la SAE).
     * Le tri doit fonctionner que le Paquet soit plein ou non.
     * https://www.youtube.com/watch?v=Ns4TPTC8whw&t=2s vidéo explicative
     */

    public Paquet trierSelection() {
        Paquet paquetCopie = new Paquet(this);
        for (int i = 0; i < this.nbCartes-1; i++){
            for (int j = i+1; j < this.nbCartes; j++){
                if (paquetCopie.cartes[i].compareTo(paquetCopie.cartes[j]) > 0){
                    Carte tmp = new Carte(paquetCopie.cartes[j]);
                    paquetCopie.cartes[j] = new Carte(paquetCopie.cartes[i]);
                    paquetCopie.cartes[i] = new Carte(tmp);
                }
            }
        }
        return paquetCopie;
    }

    /**
     * Action : Calcule et renvoie un paquet trié à partir du paquet courant (this) selon la méthode du tri bulles.
     * Le tri est effectué à partir des données du paquet courant (this) mais celui-ci ne doit pas être modifié !
     * Une nouvelle instance du paquet est traitée et renvoyée.
     * On rappelle que le paquet peut aussi contenir des cartes déjà piochées  qu'il faut ignorer (voir partie 2 de la SAE).
     * Le tri doit fonctionner que le Paquet soit plein ou non.
     * https://www.youtube.com/watch?v=lyZQPjUT5B4&embeds_referring_euri=https%3A%2F%2Fwww.developpez.com%2F&source_ve_path=Mjg2NjY&feature=emb_logo
     * vidéo explicative
     */

    public Paquet trierBulles() {
        Paquet paquetCopie = new Paquet(this);
        for (int i = 0; i < this.nbCartes-1; i++){
            for (int j = 0; j < this.nbCartes-1-i; j++){
                if (paquetCopie.cartes[j].compareTo(paquetCopie.cartes[j+1]) > 0){
                    Carte tmp = new Carte(paquetCopie.cartes[j+1]);
                    paquetCopie.cartes[j+1] = new Carte(paquetCopie.cartes[j]);
                    paquetCopie.cartes[j] = new Carte(tmp);
                }
            }
        }
        return paquetCopie;
    }

    /**
     * Action : Calcule et renvoie un paquet trié à partir du paquet courant (this) selon la méthode du tri insertion.
     * Le tri est effectué à partir des données du paquet courant (this) mais celui-ci ne doit pas être modifié !
     * Une nouvelle instance du paquet est traitée et renvoyée.
     * On rappelle que le paquet peut aussi contenir des cartes déjà piochées  qu'il faut ignorer (voir partie 2 de la SAE).
     * Le tri doit fonctionner que le Paquet soit plein ou non.
     * https://www.youtube.com/watch?v=ROalU379l3U&t=1s : vidéo explicative
     */

    public Paquet trierInsertion() {
        Paquet paquetCopie = new Paquet(this);
        for (int i = 1; i < this.nbCartes; i++) {
            Carte carteActuelle = new Carte(paquetCopie.cartes[i]);
            int j = i - 1;
            while (j >= 0 && paquetCopie.cartes[j].compareTo(carteActuelle) > 0) {
                paquetCopie.cartes[j+1] = new Carte(paquetCopie.cartes[j]);
                j--;
            }
            paquetCopie.cartes[j+1] = new Carte(carteActuelle);
        }
        return paquetCopie;
    }

    public boolean estTriee() {
        int i = 0;
        while (i < this.nbCartes-2 && this.cartes[i].compareTo(this.cartes[i + 1]) <= 0) {
            i++;
        }
        if (this.cartes[i].compareTo(this.cartes[i + 1]) > 0){
            return false;
        }
        return true;
    }

    /**
     * Action : Permet de tester les différents tris.
     * On doit s'assurer que chaque tri permette effectivement de trier un paquet mélangé.
     * Des messages d'informations devront être affichés.
     * La méthode est "static" et ne s'effectue donc pas sur la paquet courant "this".
     */
    public static void testTris() {
        Couleur[] couleurs = {Couleur.ROUGE, Couleur.JAUNE};
        Figure[] figures = {Figure.OVALE, Figure.LOSANGE};
        Texture[] textures = {Texture.PLEIN, Texture.VIDE};
        int nbFiguresMax = 2;
        Paquet paquetTest = new Paquet(couleurs, nbFiguresMax, figures, textures);
    
        long startTime, endTime, tempsExec;

        startTime = System.nanoTime();
        Paquet triSelection = paquetTest.trierSelection();
        endTime = System.nanoTime();
        tempsExec = (endTime - startTime) / 1000000;
        System.out.print("Tri sélection: ");
        System.out.println(triSelection.estTriee() ? "OK" : "Echec");
        System.out.println("Temps d'éxecution de tri sélection: " + tempsExec + " ms.");
    
        startTime = System.nanoTime();
        Paquet triBulles = paquetTest.trierBulles();
        endTime = System.nanoTime();
        tempsExec = (endTime - startTime) / 1000000;
        System.out.print("Tri bulles: ");
        System.out.println(triBulles.estTriee() ? "OK" : "Echec");
        System.out.println("Temps d'éxecution de tri bulles: " + tempsExec + " ms.");

        startTime = System.nanoTime();
        Paquet triInsertion = paquetTest.trierInsertion();
        endTime = System.nanoTime();
        tempsExec = (endTime - startTime) / 1000000;
        System.out.print("Tri insertion: ");
        System.out.println(triInsertion.estTriee() ? "OK" : "Echec");
        System.out.println("Temps d'éxecution de tri insertion: " + tempsExec + " ms.");
    }

    /**
     * Pre-requis : 0 < nbCartes <= nombre de cartes restantes dans le paquet.
     *
     * Action : Pioche nbCartes Cartes au dessus du Paquet this (et met à jour son état).
     *
     * Résultat : Un tableau contenant les nbCartes Cartes piochees dans le Paquet.
     *
     * Exemple :
     * Contenu paquet : [A,B,C,D,E,F,G]
     * Nombre de cartes restantes : 5. On considère donc seulement les cartes de 0 à 4.
     *
     * piocher(3)
     * Contenu paquet : [A,B,C,D,E,F,G]
     * Nombre de cartes restantes : 2
     * Renvoie [E,D,C]
     */

    public Carte[] piocher(int nbCartes) {
        Carte[] cartesPiochees = new Carte[nbCartes];
        for (int i = 0; i < nbCartes; i++){
            cartesPiochees[i] = this.cartes[this.nbCartes-1];
            this.nbCartes--;
        }
        return cartesPiochees;
    }

    /**
     * Résultat : Vrai s'il reste assez de cartes dans le paquet pour piocher nbCartes.
     */

    public boolean peutPicoher(int nbCartes) {
        if (this.nbCartes < nbCartes){
            return false;
        }
        return true;
    }

    /**
     * Résultat : Vrai s'il ne reste plus aucune cartes dans le paquet.
     */

    public boolean estVide() {
        if (this.nbCartes==0){
            return true;
        }
        return false;
    }

    /**
     * Résultat : Une chaîne de caractères représentant le paquet sous la forme d'un tableau
     * [X, Y, Z...] représentant les cartes restantes dans le paquet.
     *
     * Exemple :
     * Contenu paquet : 1-O-P (rouge), 2-C-V (jaune), 3-L-P (jaune), 3-L-P (rouge), 1-L-V (bleu)
     * Nombre de cartes restantes : 3
     * Retourne [1-O-P, 2-C-V, 3-L-P] (et chaque représentation d'une carte est coloré selon la couleur de la carte...)
     */

    @Override
    public String toString() {
        String str = "["; 
        for (int i = 0; i < this.nbCartes-1; i++){
            str += this.cartes[i].toString();
            if (i < this.nbCartes-2){
                str += ", ";
            }
        }
        str += "]";
        return str;
    }
}
