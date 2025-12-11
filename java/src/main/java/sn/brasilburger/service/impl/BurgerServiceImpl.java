package sn.brasilburger.service.impl;

import sn.brasilburger.entity.Burger;
import sn.brasilburger.repository.BurgerRepository;
import sn.brasilburger.service.BurgerService;
import sn.brasilburger.service.ImageStorageService;

import java.util.List;

public class BurgerServiceImpl implements BurgerService {

    private final BurgerRepository burgerRepository;
    private final ImageStorageService imageStorageService;

    public BurgerServiceImpl(BurgerRepository burgerRepository,
                             ImageStorageService imageStorageService) {
        this.burgerRepository = burgerRepository;
        this.imageStorageService = imageStorageService;
    }

    @Override
    public Burger creerBurger(String nom, double prix, String imageLocalPath) {
        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("Le nom du burger est obligatoire.");
        }
        if (prix <= 0) {
            throw new IllegalArgumentException("Le prix doit être > 0.");
        }

        String imageUrl = null;
        if (imageLocalPath != null && !imageLocalPath.isBlank()) {
            imageUrl = imageStorageService.uploadImage(imageLocalPath);
        }

        Burger burger = new Burger(nom, prix, imageUrl);
        return burgerRepository.save(burger);
    }

    @Override
    public List<Burger> listerBurgers() {
        return burgerRepository.findAll();
    }

    @Override
    public boolean supprimerBurger(int id) {
        return burgerRepository.deleteById(id);
    }
}
