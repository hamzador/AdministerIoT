package mobildev.iosm.com.priot.Model;

public class Profil {
    private String nom,prenom, dateInscription, image, id_Personne ,id_Carte;

    public Profil (){

    }

    public Profil(String nom, String prenom, String dateInscription, String image, String id_Personne, String id_Carte) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateInscription = dateInscription;
        this.image = image;
        this.id_Personne = id_Personne;
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

    public String getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(String dateInscription) {
        this.dateInscription = dateInscription;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId_Personne() {
        return id_Personne;
    }

    public void setId_Personne(String id_Personne) {
        this.id_Personne = id_Personne;
    }

    public String getId_Carte() {
        return id_Carte;
    }

    public void setId_Carte(String id_Carte) {
        this.id_Carte = id_Carte;
    }
}
