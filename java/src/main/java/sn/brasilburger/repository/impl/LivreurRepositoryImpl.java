package sn.brasilburger.repository.impl;

import sn.brasilburger.config.DbConnection;
import sn.brasilburger.entity.Gestionnaire;
import sn.brasilburger.entity.Livreur;
import sn.brasilburger.entity.Zone;
import sn.brasilburger.repository.LivreurRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivreurRepositoryImpl implements LivreurRepository {

    private final Connection connection;

    public LivreurRepositoryImpl() {
        this.connection = DbConnection.getConnection();
    }

    @Override
    public void save(Livreur livreur) {
        String sql = """
            INSERT INTO livreurs (nom, telephone, id_gestionnaire, id_zone)
            VALUES (?, ?, ?, ?)
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, livreur.getNom());
            ps.setString(2, livreur.getTelephone());
            ps.setInt(3, livreur.getGestionnaire().getId());
            ps.setInt(4, livreur.getZone().getId());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("❌ Erreur création livreur");
            e.printStackTrace();
        }
    }

    @Override
    public List<Livreur> findAll() {
        List<Livreur> livreurs = new ArrayList<>();

        String sql = """
            SELECT l.*, z.nom AS zone_nom
            FROM livreurs l
            JOIN zones z ON l.id_zone = z.id
        """;

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Livreur l = new Livreur();
                l.setId(rs.getInt("id"));
                l.setNom(rs.getString("nom"));
                l.setTelephone(rs.getString("telephone"));

                Zone z = new Zone();
                z.setId(rs.getInt("id_zone"));
                z.setNom(rs.getString("zone_nom"));
                l.setZone(z);

                livreurs.add(l);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return livreurs;
    }
}
