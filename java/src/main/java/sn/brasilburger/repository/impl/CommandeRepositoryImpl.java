package sn.brasilburger.repository.impl;

import sn.brasilburger.config.DbConnection;
import sn.brasilburger.entity.Commande;
import sn.brasilburger.entity.enums.EtatCommande;
import sn.brasilburger.entity.enums.TypeCommande;
import sn.brasilburger.repository.CommandeRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandeRepositoryImpl implements CommandeRepository {

    private final Connection connection;

    public CommandeRepositoryImpl() {
        this.connection = DbConnection.getConnection();
    }

    @Override
    public boolean save(Commande commande) {
        String sql = """
            INSERT INTO commandes
            (id_client, id_gestionnaire, id_livreur, id_zone,
             type_commande, etat_commande, total)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, commande.getClient().getId());
            ps.setInt(2, commande.getGestionnaire().getId());

            if (commande.getLivreur() != null)
                ps.setInt(3, commande.getLivreur().getId());
            else
                ps.setNull(3, Types.INTEGER);

            if (commande.getZone() != null)
                ps.setInt(4, commande.getZone().getId());
            else
                ps.setNull(4, Types.INTEGER);

            ps.setString(5, commande.getTypeCommande().name());
            ps.setString(6, commande.getEtatCommande().name());
            ps.setDouble(7, commande.getTotal());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                commande.setId(rs.getInt(1));
            }

            return true;

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'enregistrement de la commande");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Commande> findAll() {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT * FROM commandes ORDER BY id";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Commande commande = new Commande();
                commande.setId(rs.getInt("id"));
                commande.setTotal(rs.getDouble("total"));

                commande.setEtatCommande(
                        EtatCommande.valueOf(rs.getString("etat_commande"))
                );
                commande.setTypeCommande(
                        TypeCommande.valueOf(rs.getString("type_commande"))
                );

                Timestamp ts = rs.getTimestamp("date_commande");
                if (ts != null) {
                    commande.setDateCommande(ts.toLocalDateTime());
                }

                commandes.add(commande);
            }

        } catch (SQLException e) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int idCommande) {
        String sql = "DELETE FROM commandes WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idCommande);
            ps.executeUpdate();
        } catch (SQLException e) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
