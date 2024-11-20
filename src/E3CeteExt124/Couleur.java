public enum Couleur {

    /**
     * Représente la couleur d'une Carte : jaune, rouge ...
     * En plus de donner une liste énumérative des couleurs possibles,
     * cette enumération doit permettre à la méthode toString d'une Carte de réaliser un affichage en couleur.
     */
    ROUGE("\u001B[31m"),
    BLEU("\u001B[34m"),
    JAUNE("\u001B[33m");

    private String abreviation ;

    private Couleur (String a){
        this.abreviation=a;
    }  

    public String getAbreviation() {
        return this.abreviation ;
    }
}
