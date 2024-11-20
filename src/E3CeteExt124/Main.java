public class Main {

    /**
     * Action : lance une partie de jeu "E3Cète"
     */
    public static void main(String[] args) {
        /*Carte carte1 = new Carte(Couleur.ROUGE, 2, Figure.OVALE, Texture.PLEIN);
        Carte carte2 = new Carte(Couleur.BLEU, 3, Figure.LOZANGE, Texture.HACHURÉ);
        System.out.println("Carte 1: " + carte1.toString());
        System.out.println("Carte 2: " + carte2.toString());
        
        int comparaison = carte1.compareTo(carte2);
        System.out.println("Résultat de la comparaison: " + comparaison);

        System.out.println("Nombre de figures dans carte1: " + carte1.getNbFigures());
        System.out.println("Figure de carte1: " + carte1.getFigure());
        System.out.println("Couleur de carte1: " + carte1.getCouleur());
        System.out.println("Texture de carte1: " + carte1.getTexture());

        System.out.println("Représentation de carte1: " + carte1.toString());
        System.out.println("Représentation de carte2: " + carte2.toString());*/

        /*Couleur[] couleurs = {Couleur.ROUGE,Couleur.BLEU, Couleur.JAUNE};
        Figure[] figures = {Figure.OVALE, Figure.LOSANGE,Figure.CARRE};
        Texture[] textures = {Texture.PLEIN, Texture.VIDE, Texture.HACHURÉ};
        Paquet paquet = new Paquet(couleurs,3,figures,textures);
        Paquet bulles = new Paquet(paquet.trierBulles());
        System.out.println(bulles.toString());
        //Paquet copie = new Paquet(paquet);*/
        Jeu jeu = new Jeu(3,3);
        jeu.jouer();
        //float result = jeu.proba3CR(1000000);
        //System.out.println(result);
    }
}
