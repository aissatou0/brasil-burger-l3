package sn.brasilburger.service;

import sn.brasilburger.entity.Livreur;
import java.util.List;

public interface LivreurService {

    void creerLivreur(Livreur livreur);

    List<Livreur> listerLivreurs();
}
