package sn.brasilburger.service;

import sn.brasilburger.entity.Complement;
import sn.brasilburger.entity.enums.TypeComplement;

import java.util.List;

public interface ComplementService {

    void ajouterComplement(
            String nom,
            double prix,
            TypeComplement type,
            String cheminImage
    );

    List<Complement> listerComplements();

    void supprimerComplement(int id);
}
