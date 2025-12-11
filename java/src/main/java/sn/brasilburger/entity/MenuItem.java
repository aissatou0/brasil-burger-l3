package sn.brasilburger.entity;

import sn.brasilburger.entity.enums.TypeItem;

public class MenuItem {

    private int id;
    private TypeItem typeItem; // BURGER / COMPLEMENT
    private int idItem;
    private int quantite;

    public MenuItem() {}

    public MenuItem(TypeItem typeItem, int idItem, int quantite) {
        this.typeItem = typeItem;
        this.idItem = idItem;
        this.quantite = quantite;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public TypeItem getTypeItem() { return typeItem; }
    public void setTypeItem(TypeItem typeItem) { this.typeItem = typeItem; }

    public int getIdItem() { return idItem; }
    public void setIdItem(int idItem) { this.idItem = idItem; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
}
