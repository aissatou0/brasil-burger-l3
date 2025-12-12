package sn.brasilburger.repository.impl;

import sn.brasilburger.config.DbConnection;
import sn.brasilburger.entity.Complement;
import sn.brasilburger.entity.enums.TypeComplement;
import sn.brasilburger.repository.ComplementRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComplementRepositoryImpl implements ComplementRepository {

    private final Connection connection;

    public ComplementRepositoryImpl() {
        this.connection = DbConnection.getConnection();
    }

    @Override
    public void save(Complement complement) {
        String sql = """
            INSERT INTO complements (nom, prix, type_complement, image, actif)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, complement.getNom());
            ps.setDouble(2, complement.getPrix());
            ps.setString(3, complement.getTypeComplement().name());
            ps.setString(4, complement.getImage());
            ps.setBoolean(5, complement.isActif());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'enregistrement du complément");
            e.printStackTrace();
        }
    }

    @Override
    public List<Complement> findAll() {
        List<Complement> complements = new ArrayList<>();
        String sql = "SELECT * FROM complements ORDER BY id";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Complement c = new Complement();
                c.setId(rs.getInt("id"));
                c.setNom(rs.getString("nom"));
                c.setPrix(rs.getDouble("prix"));
                c.setTypeComplement(TypeComplement.valueOf(rs.getString("type_complement")));
                c.setImage(rs.getString("image"));
                c.setActif(rs.getBoolean("actif"));

                complements.add(c);
            }

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors du chargement des compléments");
            e.printStackTrace();
        }

        return complements;
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM complements WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la suppression du complément");
            e.printStackTrace();
        }
    }

    @Override
public double findPrixById(int idComplement) {
    String sql = "SELECT prix FROM complements WHERE id = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, idComplement);
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
