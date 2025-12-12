package sn.brasilburger.repository;

import sn.brasilburger.entity.Gestionnaire;
import java.util.List;

public interface GestionnaireRepository {

    void save(Gestionnaire gestionnaire);

    List<Gestionnaire> findAll();

    Gestionnaire findById(int id);
}
