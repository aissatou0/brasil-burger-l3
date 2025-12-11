package sn.brasilburger.entity;

import sn.brasilburger.entity.enums.TypeItem;

public class CommandeItem {

    private int id;
    private TypeItem typeItem;
    private int idItem;
    private int quantite;
    private double prix;
    private int idCommande;

    public CommandeItem() {}

    public CommandeItem(int id, TypeItem typeItem, int idItem, int quantite, double prix) {
        this.id = id;
        this.typeItem = typeItem;
        this.idItem = idItem;
        this.quantite = quantite;
        this.prix = prix;
    }
    public int getIdCommande() {
    return idCommande;
}

public void setIdCommande(int idCommande) {
    this.idCommande = idCommande;
}
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public TypeItem getTypeItem() { return typeItem; }
    public void setTypeItem(TypeItem typeItem) { this.typeItem = typeItem; }

    public int getIdItem() { return idItem; }
    public void setIdItem(int idItem) { this.idItem = idItem; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }
}
