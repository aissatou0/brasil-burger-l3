package sn.brasilburger.entity;

public class Livreur {

    private int id;
    private String nom;
    private String telephone;

    private Gestionnaire gestionnaire;
    private Zone zone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Gestionnaire getGestionnaire() {
        return gestionnaire;
    }

    public void setGestionnaire(Gestionnaire gestionnaire) {
        this.gestionnaire = gestionnaire;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    @Override
    public String toString() {
        return id + " - " + nom +
                " | Tel: " + telephone +
                " | Zone: " + (zone != null ? zone.getNom() : "N/A");
    }
}
