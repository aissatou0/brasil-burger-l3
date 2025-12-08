package sn.brasilburger.entity;

import java.util.ArrayList;
import java.util.List;

public class Client {

    private int id;
    private String nom;
    private String prenom;
    private String telephone;
    private String email;
    private String password;

    // Relation 1..* avec Commande (à compléter plus tard)
    private List<Commande> commandes = new ArrayList<>();

    public Client() {
    }

    public Client(int id, String nom, String prenom, String telephone, String email, String password) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.email = email;
        this.password = password;
    }

    public Client(String nom, String prenom, String telephone, String email, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.email = email;
        this.password = password;
    }

    // Getters / Setters

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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Commande> getCommandes() {
        return commandes;
    }

    public void addCommande(Commande commande) {
        if (commande != null && !this.commandes.contains(commande)) {
            this.commandes.add(commande);
        }
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
