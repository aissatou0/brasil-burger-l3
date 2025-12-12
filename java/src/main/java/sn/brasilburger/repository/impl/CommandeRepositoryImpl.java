package sn.brasilburger.repository.impl;

import sn.brasilburger.config.DbConnection;
import sn.brasilburger.entity.Commande;
import sn.brasilburger.repository.CommandeRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandeRepositoryImpl implements CommandeRepository {

    private Connection connection;

    public CommandeRepositoryImpl() {
        this.connection = DbConnection.getConnection();
    }

    @Override
    public void save(Commande commande) {
        String sql = "INSERT INTO commandes (id_client, type_commande, etat_commande, total) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, commande.getClient().getId());
            ps.setString(2, commande.getTypeCommande().name());
            ps.setString(3, commande.getEtatCommande().name());
            ps.setDouble(4, commande.getTotal());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                commande.setId(rs.getInt(1));
            }

            System.out.println("✅ Commande enregistrée avec succès.");

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'enregistrement de la commande");
            e.printStackTrace();
        }
    }

    @Override
    public List<Commande> findAll() {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT * FROM commandes";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Commande commande = new Commande();
                commande.setId(rs.getInt("id"));
                commande.setTotal(rs.getDouble("total"));
                commande.setEtatCommande(
                        Enum.valueOf(
                                sn.brasilburger.entity.enums.EtatCommande.class,
                                rs.getString("etat_commande")
                        )
                );

                commandes.add(commande);
            }

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors du chargement des commandes");
            e.printStackTrace();
        }

        return commandes;
    }

    @Override
    public void updateEtat(int idCommande, String nouvelEtat) {
        String sql = "UPDATE commandes SET etat_commande = ? WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, nouvelEtat);
            ps.setInt(2, idCommande);

            ps.executeUpdate();
            System.out.println("✅ État de la commande mis à jour.");

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la mise à jour de l'état");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int idCommande) {
        String sql = "DELETE FROM commandes WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idCommande);
            ps.executeUpdate();

            System.out.println("✅ Commande supprimée avec succès.");

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la suppression");
            e.printStackTrace();
        }
    }

    @Override
public void updateTotal(int idCommande, double total) {
    String sql = "UPDATE commandes SET total = ? WHERE id = ?";

    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setDouble(1, total);
        ps.setInt(2, idCommande);
        ps.executeUpdate();
    } catch (Exception e) {
        System.out.println("❌ Erreur mise à jour total commande");
        e.printStackTrace();
    }
}

}
