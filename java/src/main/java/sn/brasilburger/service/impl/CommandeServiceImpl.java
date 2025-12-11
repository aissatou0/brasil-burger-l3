package sn.brasilburger.service.impl;

import sn.brasilburger.entity.Commande;
import sn.brasilburger.repository.CommandeRepository;
import sn.brasilburger.service.CommandeService;

import java.util.List;

public class CommandeServiceImpl implements CommandeService {

    private final CommandeRepository commandeRepository;

    public CommandeServiceImpl(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    @Override
    public void creerCommande(Commande commande) {
        if (commande == null) {
            System.out.println("❌ Commande invalide.");
            return;
        }
        commandeRepository.save(commande);
    }

    @Override
    public List<Commande> listerCommandes() {
        return commandeRepository.findAll();
    }

    @Override
    public void changerEtat(int idCommande, String nouvelEtat) {
        if (idCommande <= 0) {
            System.out.println("❌ ID invalide.");
            return;
        }
        commandeRepository.updateEtat(idCommande, nouvelEtat);
    }

    @Override
    public void supprimerCommande(int idCommande) {
        if (idCommande <= 0) {
            System.out.println("❌ ID invalide.");
            return;
        }
        commandeRepository.delete(idCommande);
    }
}
