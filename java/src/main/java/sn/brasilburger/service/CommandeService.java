package sn.brasilburger.service;

import sn.brasilburger.entity.Commande;

import java.util.List;

public interface CommandeService {

    void creerCommande(Commande commande);

    List<Commande> listerCommandes();

    void changerEtat(int idCommande, String nouvelEtat);

    void supprimerCommande(int idCommande);
}
