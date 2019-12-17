package mobildev.iosm.com.priot.Model;

public class Presence {
    private String entreSortie,id_Carte,nom,prenom,image,dateEntre;

    public Presence(){

    }

    public Presence(String entreSortie, String id_Carte, String nom, String prenom, String image, String dateEntre) {
        this.entreSortie = entreSortie;
        this.id_Carte = id_Carte;
        this.nom = nom;
        this.prenom = prenom;
        this.image=image;
        this.dateEntre=dateEntre;
    }

    public String getDateEntre() {
        return dateEntre;
    }

    public void setDateEntre(String dateEntre) {
        this.dateEntre = dateEntre;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEntreSortie() {
        return entreSortie;
    }

    public void setEntreSortie(String entreSortie) {
        this.entreSortie = entreSortie;
    }

    public String getId_Carte() {
        return id_Carte;
    }

    public void setId_Carte(String id_Carte) {
        this.id_Carte = id_Carte;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}
