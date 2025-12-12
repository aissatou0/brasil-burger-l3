package sn.brasilburger.repository;

import sn.brasilburger.entity.Paiement;

public interface PaiementRepository {
    boolean existsByCommande(int idCommande);
    void save(Paiement paiement);
}
