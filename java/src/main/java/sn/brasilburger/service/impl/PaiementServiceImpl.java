package sn.brasilburger.service.impl;

import sn.brasilburger.entity.Commande;
import sn.brasilburger.entity.Paiement;
import sn.brasilburger.entity.enums.ModePaiement;
import sn.brasilburger.repository.CommandeItemRepository;
import sn.brasilburger.repository.CommandeRepository;
import sn.brasilburger.repository.PaiementRepository;
import sn.brasilburger.service.PaiementService;

import java.math.BigDecimal;

public class PaiementServiceImpl implements PaiementService {

    private final PaiementRepository paiementRepository;
    private final CommandeRepository commandeRepository;
    private final CommandeItemRepository commandeItemRepository;

    public PaiementServiceImpl(
            PaiementRepository paiementRepository,
            CommandeRepository commandeRepository,
            CommandeItemRepository commandeItemRepository
    ) {
        this.paiementRepository = paiementRepository;
        this.commandeRepository = commandeRepository;
        this.commandeItemRepository = commandeItemRepository;
    }

    @Override
    public void payerCommande(int idCommande, ModePaiement mode) {
        if (idCommande <= 0) {
            System.out.println("❌ ID commande invalide.");
            return;
        }

        // 1) paiement déjà existant ?
        if (paiementRepository.existsByCommande(idCommande)) {
            System.out.println("❌ Cette commande est déjà payée.");
            return;
        }

        // 2) total réel (depuis les items)
        double total = commandeItemRepository.calculerTotalCommande(idCommande);
        if (total <= 0) {
            System.out.println("❌ Impossible de payer : commande vide (aucun item).");
            return;
        }

        // 3) sync total (au cas où)
        commandeRepository.updateTotal(idCommande, total);

        // 4) enregistrer paiement
        Paiement paiement = new Paiement();
        paiement.setIdCommande(idCommande);
        paiement.setMode(mode);
        paiement.setMontant(BigDecimal.valueOf(total));

        paiementRepository.save(paiement);

        // 5) passer commande en VALIDEE
        commandeRepository.updateEtat(idCommande, "VALIDEE");

        System.out.println("✅ Commande payée (montant: " + total + ", mode: " + mode + ")");
    }
}
