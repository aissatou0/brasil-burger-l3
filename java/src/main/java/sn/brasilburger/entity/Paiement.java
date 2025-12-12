package sn.brasilburger.entity;

import sn.brasilburger.entity.enums.ModePaiement;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Paiement {
    private int id;
    private int idCommande;
    private LocalDateTime datePaiement;
    private BigDecimal montant;
    private ModePaiement mode;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdCommande() { return idCommande; }
    public void setIdCommande(int idCommande) { this.idCommande = idCommande; }

    public LocalDateTime getDatePaiement() { return datePaiement; }
    public void setDatePaiement(LocalDateTime datePaiement) { this.datePaiement = datePaiement; }

    public BigDecimal getMontant() { return montant; }
    public void setMontant(BigDecimal montant) { this.montant = montant; }

    public ModePaiement getMode() { return mode; }
    public void setMode(ModePaiement mode) { this.mode = mode; }

    @Override
    public String toString() {
        return "Paiement{id=" + id +
                ", idCommande=" + idCommande +
                ", datePaiement=" + datePaiement +
                ", montant=" + montant +
                ", mode=" + mode + "}";
    }
}
