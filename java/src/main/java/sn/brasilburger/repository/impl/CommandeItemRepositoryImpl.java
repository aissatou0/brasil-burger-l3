package sn.brasilburger.repository.impl;

import sn.brasilburger.config.DbConnection;
import sn.brasilburger.entity.CommandeItem;
import sn.brasilburger.repository.CommandeItemRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CommandeItemRepositoryImpl implements CommandeItemRepository {

    private final Connection connection;

    public CommandeItemRepositoryImpl() {
        this.connection = DbConnection.getConnection();
    }

    @Override
public void save(int idCommande, CommandeItem item) {
    String sql = """
        INSERT INTO commande_items
        (id_commande, type_item, id_item, quantite, prix)
        VALUES (?, ?, ?, ?, ?)
    """;

    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, idCommande);
        ps.setString(2, item.getTypeItem().name());
        ps.setInt(3, item.getIdItem());
        ps.setInt(4, item.getQuantite());
        ps.setDouble(5, item.getPrix());
        ps.executeUpdate();
    } catch (Exception e) {
        System.out.println("❌ Erreur ajout item commande");
        e.printStackTrace();
    }
}


    @Override
    public List<CommandeItem> findByCommande(int idCommande) {
        List<CommandeItem> items = new ArrayList<>();

        String sql = """
            SELECT * FROM commande_items
            WHERE id_commande = ?
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idCommande);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CommandeItem item = new CommandeItem();
                item.setId(rs.getInt("id"));
                item.setIdCommande(idCommande);
                item.setTypeItem(
    sn.brasilburger.entity.enums.TypeItem.valueOf(rs.getString("type_item"))
);

                item.setIdItem(rs.getInt("id_item"));
                item.setQuantite(rs.getInt("quantite"));
                item.setPrix(rs.getDouble("prix"));

                items.add(item);
            }
        } catch (Exception e) {
            System.out.println("❌ Erreur récupération items commande");
            e.printStackTrace();
        }

        return items;
    }

    @Override
    public void deleteByCommande(int idCommande) {
        String sql = "DELETE FROM commande_items WHERE id_commande = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idCommande);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("❌ Erreur suppression items commande");
            e.printStackTrace();
        }
    }

    @Override
public double calculerTotalCommande(int idCommande) {
    String sql = "SELECT SUM(prix) FROM commande_items WHERE id_commande = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, idCommande);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getDouble(1);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return 0;
}

}
