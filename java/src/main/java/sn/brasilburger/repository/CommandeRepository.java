package sn.brasilburger.repository;

import sn.brasilburger.entity.Commande;
import java.util.List;

public interface CommandeRepository {

    boolean save(Commande commande);

    List<Commande> findAll();

    void updateEtat(int idCommande, String nouvelEtat);

    void delete(int idCommande);

    void updateTotal(int idCommande, double total);
}
