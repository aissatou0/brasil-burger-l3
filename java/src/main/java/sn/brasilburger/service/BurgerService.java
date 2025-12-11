package sn.brasilburger.service;

import sn.brasilburger.entity.Burger;

import java.util.List;

public interface BurgerService {

    Burger creerBurger(String nom, double prix, String imageLocalPath);

    List<Burger> listerBurgers();

    boolean supprimerBurger(int id);
}
