package sn.brasilburger.repository;

import sn.brasilburger.entity.Burger;

import java.util.List;

public interface BurgerRepository {

    Burger save(Burger burger);

    List<Burger> findAll();

    boolean deleteById(int id);
    double findPrixById(int idBurger);

}
