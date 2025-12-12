package sn.brasilburger.repository;

import sn.brasilburger.entity.Livreur;
import java.util.List;

public interface LivreurRepository {

    void save(Livreur livreur);

    List<Livreur> findAll();
}
