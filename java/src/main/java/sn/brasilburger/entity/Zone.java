package sn.brasilburger.entity;

public class Zone {

    private int id;
    private String nom;
    private double prixLivraison;

    public Zone() {}

    public Zone(int id, String nom, double prixLivraison) {
        this.id = id;
        this.nom = nom;
        this.prixLivraison = prixLivraison;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public double getPrixLivraison() { return prixLivraison; }
    public void setPrixLivraison(double prixLivraison) { this.prixLivraison = prixLivraison; }
}
