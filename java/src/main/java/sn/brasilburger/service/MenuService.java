package sn.brasilburger.service;

import sn.brasilburger.entity.Menu;
import sn.brasilburger.entity.MenuItem;
import sn.brasilburger.entity.enums.TypeItem;

import java.util.List;

public interface MenuService {

    void creerMenu(String nom, String cheminImage);

    void ajouterItem(int menuId, TypeItem typeItem, int idItem, int quantite);

    List<Menu> listerMenus();

    void supprimerMenu(int id);
}
