package sn.brasilburger.repository;

import sn.brasilburger.entity.CommandeItem;

import java.util.List;

public interface CommandeItemRepository {

    void save(CommandeItem item);

    List<CommandeItem> findByCommande(int idCommande);

    void deleteByCommande(int idCommande);
}
