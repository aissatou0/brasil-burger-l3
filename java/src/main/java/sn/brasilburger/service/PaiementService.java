package sn.brasilburger.service;

import sn.brasilburger.entity.enums.ModePaiement;

public interface PaiementService {
    void payerCommande(int idCommande, ModePaiement mode);
}
