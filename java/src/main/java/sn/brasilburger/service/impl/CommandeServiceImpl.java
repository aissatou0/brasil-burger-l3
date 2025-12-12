package sn.brasilburger.service.impl;

import sn.brasilburger.entity.Commande;
import sn.brasilburger.entity.CommandeItem;
import sn.brasilburger.entity.enums.TypeItem;
import sn.brasilburger.repository.BurgerRepository;
import sn.brasilburger.repository.CommandeItemRepository;
import sn.brasilburger.repository.CommandeRepository;
import sn.brasilburger.repository.ComplementRepository;
import sn.brasilburger.repository.MenuRepository;
import sn.brasilburger.service.CommandeService;

import java.util.List;

public class CommandeServiceImpl implements CommandeService {

    private final CommandeRepository commandeRepository;
    private final CommandeItemRepository commandeItemRepository;
    private final BurgerRepository burgerRepository;
    private final ComplementRepository complementRepository;
    private final MenuRepository menuRepository;

    public CommandeServiceImpl(
            CommandeRepository commandeRepository,
            CommandeItemRepository commandeItemRepository,
            BurgerRepository burgerRepository,
            ComplementRepository complementRepository,
            MenuRepository menuRepository
    ) {
        this.commandeRepository = commandeRepository;
        this.commandeItemRepository = commandeItemRepository;
        this.burgerRepository = burgerRepository;
        this.complementRepository = complementRepository;
        this.menuRepository = menuRepository;
    }

    @Override
public boolean creerCommande(Commande commande) {
    if (commande == null || commande.getClient() == null || commande.getGestionnaire() == null) {
        System.out.println("❌ Commande invalide");
        return false;
    }
    return commandeRepository.save(commande);
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

    @Override
    public void ajouterItem(int idCommande, TypeItem typeItem, int idItem, int quantite) {

        if (quantite <= 0) {
            throw new IllegalArgumentException("La quantité doit être supérieure à 0");
        }

        double prixUnitaire;

        switch (typeItem) {
            case BURGER -> prixUnitaire = burgerRepository.findPrixById(idItem);
            case COMPLEMENT -> prixUnitaire = complementRepository.findPrixById(idItem);
            case MENU -> prixUnitaire = menuRepository.calculerPrixMenu(idItem);
            default -> throw new IllegalArgumentException("Type d'item invalide");
        }

        CommandeItem item = new CommandeItem();
        item.setTypeItem(typeItem);
        item.setIdItem(idItem);
        item.setQuantite(quantite);
        item.setPrix(prixUnitaire * quantite);

        // Enregistrement de l’item
        commandeItemRepository.save(idCommande, item);

        // Recalcul du total
        double total = commandeItemRepository.calculerTotalCommande(idCommande);
        commandeRepository.updateTotal(idCommande, total);
    }
}
