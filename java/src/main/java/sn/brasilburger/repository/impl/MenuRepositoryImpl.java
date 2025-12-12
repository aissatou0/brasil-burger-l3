package sn.brasilburger.repository.impl;

import sn.brasilburger.config.DbConnection;
import sn.brasilburger.entity.Menu;
import sn.brasilburger.entity.MenuItem;
import sn.brasilburger.entity.enums.TypeItem;
import sn.brasilburger.repository.MenuRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuRepositoryImpl implements MenuRepository {

    private final Connection connection;

    public MenuRepositoryImpl() {
        this.connection = DbConnection.getConnection();
    }

    @Override
    public int save(Menu menu) {
        String sql = "INSERT INTO menus (nom, image, actif) VALUES (?, ?, ?) RETURNING id";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, menu.getNom());
            ps.setString(2, menu.getImage());
            ps.setBoolean(3, menu.isActif());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur insertion menu");
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void addItemToMenu(int menuId, MenuItem item) {
        String sql = """
            INSERT INTO menu_items (id_menu, type_item, id_item, quantite)
            VALUES (?, ?, ?, ?)
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, menuId);
            ps.setString(2, item.getTypeItem().name());
            ps.setInt(3, item.getIdItem());
            ps.setInt(4, item.getQuantite());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("❌ Erreur ajout item menu");
            e.printStackTrace();
        }
    }

    @Override
    public List<Menu> findAll() {
        List<Menu> menus = new ArrayList<>();
        String sql = "SELECT * FROM menus";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Menu menu = new Menu();
                menu.setId(rs.getInt("id"));
                menu.setNom(rs.getString("nom"));
                menu.setImage(rs.getString("image"));
                menu.setActif(rs.getBoolean("actif"));
                menus.add(menu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menus;
    }

    @Override
    public Menu findById(int id) {
        String sql = "SELECT * FROM menus WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Menu menu = new Menu();
                menu.setId(rs.getInt("id"));
                menu.setNom(rs.getString("nom"));
                menu.setImage(rs.getString("image"));
                menu.setActif(rs.getBoolean("actif"));
                return menu;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM menus WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
public double calculerPrixMenu(int idMenu) {
    String sql = """
        SELECT SUM(
            CASE 
                WHEN mi.type_item = 'BURGER' THEN b.prix
                WHEN mi.type_item = 'COMPLEMENT' THEN c.prix
            END * mi.quantite
        )
        FROM menu_items mi
        LEFT JOIN burgers b ON mi.id_item = b.id AND mi.type_item = 'BURGER'
        LEFT JOIN complements c ON mi.id_item = c.id AND mi.type_item = 'COMPLEMENT'
        WHERE mi.id_menu = ?
    """;

    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, idMenu);
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
