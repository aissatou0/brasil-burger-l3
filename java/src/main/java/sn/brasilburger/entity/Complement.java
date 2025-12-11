package sn.brasilburger.entity;

import sn.brasilburger.entity.enums.TypeComplement;

public class Complement {

    private int id;
    private String nom;
    private double prix;
    private TypeComplement typeComplement;
    private String image;
    private boolean actif;

    public Complement() {
        this.actif = true;
    }

    public Complement(String nom, double prix, TypeComplement typeComplement, String image) {
        this.nom = nom;
        this.prix = prix;
        this.typeComplement = typeComplement;
        this.image = image;
        this.actif = true;
    }

    // GETTERS / SETTERS

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }

    public TypeComplement getTypeComplement() { return typeComplement; }
    public void setTypeComplement(TypeComplement typeComplement) { this.typeComplement = typeComplement; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public boolean isActif() { return actif; }
    public void setActif(boolean actif) { this.actif = actif; }

    @Override
    public String toString() {
        return "Complement{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                ", type=" + typeComplement +
                ", actif=" + actif +
                '}';
    }
}
