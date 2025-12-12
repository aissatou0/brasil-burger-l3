package sn.brasilburger.repository.impl;

import sn.brasilburger.config.DbConnection;
import sn.brasilburger.entity.Paiement;
import sn.brasilburger.repository.PaiementRepository;

import java.sql.*;

public class PaiementRepositoryImpl implements PaiementRepository {

    private final Connection connection;

    public PaiementRepositoryImpl() {
        this.connection = DbConnection.getConnection();
    }

    @Override
    public boolean existsByCommande(int idCommande) {
        String sql = "SELECT 1 FROM paiements WHERE id_commande = ? LIMIT 1";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idCommande);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            System.out.println("❌ Erreur vérification paiement existant");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void save(Paiement paiement) {
        String sql = "INSERT INTO paiements (id_commande, montant, mode) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, paiement.getIdCommande());
            ps.setBigDecimal(2, paiement.getMontant());
            ps.setString(3, paiement.getMode().name());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) paiement.setId(rs.getInt(1));
            }

            System.out.println("✅ Paiement enregistré.");
        } catch (Exception e) {
            System.out.println("❌ Erreur enregistrement paiement");
            e.printStackTrace();
        }
    }
}
