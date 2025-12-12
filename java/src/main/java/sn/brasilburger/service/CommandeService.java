package sn.brasilburger.service;

import sn.brasilburger.entity.Commande;
import sn.brasilburger.entity.enums.TypeItem;

import java.util.List;

public interface CommandeService {

    void creerCommande(Commande commande);

    List<Commande> listerCommandes();

    void changerEtat(int idCommande, String nouvelEtat);

    void supprimerCommande(int idCommande);

    void ajouterItem(
            int idCommande,
            TypeItem typeItem,
            int idItem,
            int quantite
    );
}
