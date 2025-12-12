package sn.brasilburger.service.impl;

import sn.brasilburger.entity.Gestionnaire;
import sn.brasilburger.repository.GestionnaireRepository;
import sn.brasilburger.service.GestionnaireService;

import java.util.List;

public class GestionnaireServiceImpl implements GestionnaireService {

    private final GestionnaireRepository repository;

    public GestionnaireServiceImpl(GestionnaireRepository repository) {
        this.repository = repository;
    }

    @Override
    public void creerGestionnaire(Gestionnaire gestionnaire) {
        repository.save(gestionnaire);
    }

    @Override
    public List<Gestionnaire> listerGestionnaires() {
        return repository.findAll();
    }

    @Override
    public Gestionnaire getById(int id) {
        return repository.findById(id);
    }
}
