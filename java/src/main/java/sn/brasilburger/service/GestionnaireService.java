package sn.brasilburger.service;

import sn.brasilburger.entity.Gestionnaire;
import java.util.List;

public interface GestionnaireService {

    void creerGestionnaire(Gestionnaire gestionnaire);

    List<Gestionnaire> listerGestionnaires();

    Gestionnaire getById(int id);
}
