package sn.brasilburger.entity;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    private int id;
    private String nom;
    private String image;
    private boolean actif = true;

    private List<MenuItem> items = new ArrayList<>();

    public Menu() {}

    public Menu(String nom, String image) {
        this.nom = nom;
        this.image = image;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public boolean isActif() { return actif; }
    public void setActif(boolean actif) { this.actif = actif; }

    public List<MenuItem> getItems() { return items; }

    public void addItem(MenuItem item) {
        if (item != null) items.add(item);
    }

    @Override
public String toString() {
    return "Menu{" +
            "id=" + id +
            ", nom='" + nom + '\'' +
            ", image='" + image + '\'' +
            ", actif=" + actif +
            '}';
}

}
