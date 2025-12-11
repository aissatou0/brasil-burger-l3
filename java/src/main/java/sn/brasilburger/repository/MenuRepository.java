package sn.brasilburger.repository;

import sn.brasilburger.entity.Menu;
import sn.brasilburger.entity.MenuItem;

import java.util.List;

public interface MenuRepository {

    int save(Menu menu);

    void addItemToMenu(int menuId, MenuItem item);

    List<Menu> findAll();

    Menu findById(int id);

    void delete(int id);
}
