package sn.brasilburger.service.impl;

import sn.brasilburger.entity.Menu;
import sn.brasilburger.entity.MenuItem;
import sn.brasilburger.entity.enums.TypeItem;
import sn.brasilburger.repository.MenuRepository;
import sn.brasilburger.service.ImageStorageService;
import sn.brasilburger.service.MenuService;

import java.util.List;

public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final ImageStorageService imageStorageService;

    public MenuServiceImpl(MenuRepository menuRepository,
                           ImageStorageService imageStorageService) {
        this.menuRepository = menuRepository;
        this.imageStorageService = imageStorageService;
    }

    @Override
    public void creerMenu(String nom, String cheminImage) {

        String imageUrl = null;
        if (cheminImage != null && !cheminImage.isBlank()) {
            imageUrl = imageStorageService.uploadImage(cheminImage);
        }

        Menu menu = new Menu();
        menu.setNom(nom);
        menu.setImage(imageUrl);
        menu.setActif(true);

        int menuId = menuRepository.save(menu);

        if (menuId > 0) {
            System.out.println("✅ Menu créé avec ID = " + menuId);
        } else {
            System.out.println("❌ Échec création menu");
        }
    }

    @Override
    public void ajouterItem(int menuId, TypeItem typeItem, int idItem, int quantite) {

        MenuItem item = new MenuItem();
        item.setTypeItem(typeItem);
        item.setIdItem(idItem);
        item.setQuantite(quantite);

        menuRepository.addItemToMenu(menuId, item);
        System.out.println("✅ Item ajouté au menu");
    }

    @Override
    public List<Menu> listerMenus() {
        return menuRepository.findAll();
    }

    @Override
    public void supprimerMenu(int id) {
        menuRepository.delete(id);
        System.out.println("✅ Menu supprimé");
    }
}
