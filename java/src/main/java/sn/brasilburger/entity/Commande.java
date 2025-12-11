package sn.brasilburger.entity;

import sn.brasilburger.entity.enums.EtatCommande;
import sn.brasilburger.entity.enums.TypeCommande;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Commande {

    private int id;
    private LocalDateTime dateCommande;
    private double total;
    private EtatCommande etatCommande;
    private TypeCommande typeCommande;
    

    // Relations
    private Client client;
    private Zone zone;
    private Livreur livreur;
    private Gestionnaire gestionnaire;

    // 1..* avec CommandeItem
    private List<CommandeItem> items = new ArrayList<>();

    public Commande() {
        this.dateCommande = LocalDateTime.now();
        this.etatCommande = EtatCommande.EN_COURS;
    }

    public Commande(Client client, TypeCommande typeCommande) {
        this();
        this.client = client;
        this.typeCommande = typeCommande;
    }

    public Commande(int id, LocalDateTime dateCommande, double total,
                    EtatCommande etatCommande, TypeCommande typeCommande,
                    Client client, Zone zone, Livreur livreur, Gestionnaire gestionnaire) {
        this.id = id;
        this.dateCommande = dateCommande;
        this.total = total;
        this.etatCommande = etatCommande;
        this.typeCommande = typeCommande;
        this.client = client;
        this.zone = zone;
        this.livreur = livreur;
        this.gestionnaire = gestionnaire;
    }

    // ================= GETTERS / SETTERS =================
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDateTime dateCommande) {
        this.dateCommande = dateCommande;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public EtatCommande getEtatCommande() {
        return etatCommande;
    }

    public void setEtatCommande(EtatCommande etatCommande) {
        this.etatCommande = etatCommande;
    }

    public TypeCommande getTypeCommande() {
        return typeCommande;
    }

    public void setTypeCommande(TypeCommande typeCommande) {
        this.typeCommande = typeCommande;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public Livreur getLivreur() {
        return livreur;
    }

    public void setLivreur(Livreur livreur) {
        this.livreur = livreur;
    }

    public Gestionnaire getGestionnaire() {
        return gestionnaire;
    }

    public void setGestionnaire(Gestionnaire gestionnaire) {
        this.gestionnaire = gestionnaire;
    }

    public List<CommandeItem> getItems() {
        return items;
    }

    public void addItem(CommandeItem item) {
        if (item != null && !this.items.contains(item)) {
            this.items.add(item);
        }
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", dateCommande=" + dateCommande +
                ", total=" + total +
                ", etatCommande=" + etatCommande +
                ", typeCommande=" + typeCommande +
                ", client=" + (client != null ? client.getNom() : "N/A") +
                '}';
    }
}
