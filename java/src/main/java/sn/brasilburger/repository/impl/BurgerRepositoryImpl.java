package sn.brasilburger.repository.impl;

import sn.brasilburger.config.DbConnection;
import sn.brasilburger.entity.Burger;
import sn.brasilburger.repository.BurgerRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BurgerRepositoryImpl implements BurgerRepository {

    private final Connection connection;

    public BurgerRepositoryImpl() {
        this.connection = DbConnection.getConnection();
    }

    @Override
    public Burger save(Burger burger) {
        String sql = "INSERT INTO burgers (nom, prix, image, actif) " +
                     "VALUES (?, ?, ?, TRUE) RETURNING id";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, burger.getNom());
            ps.setDouble(2, burger.getPrix());
            ps.setString(3, burger.getImage());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    burger.setId(rs.getInt("id"));
                }
            }
            return burger;
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'enregistrement du burger");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Burger> findAll() {
        List<Burger> burgers = new ArrayList<>();
        String sql = "SELECT id, nom, prix, image, actif FROM burgers ORDER BY id";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Burger burger = new Burger(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getDouble("prix"),
                        rs.getString("image"),
                        rs.getBoolean("actif")
                );
                burgers.add(burger);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors du chargement des burgers");
            e.printStackTrace();
        }

        return burgers;
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM burgers WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la suppression du burger");
            e.printStackTrace();
            return false;
        }
    }

    @Override
public double findPrixById(int idBurger) {
    String sql = "SELECT prix FROM burgers WHERE id = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, idBurger);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getDouble("prix");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return 0;
}

}
