package sn.brasilburger.service.impl;

import sn.brasilburger.entity.Livreur;
import sn.brasilburger.repository.LivreurRepository;
import sn.brasilburger.service.LivreurService;

import java.util.List;

public class LivreurServiceImpl implements LivreurService {

    private final LivreurRepository repository;

    public LivreurServiceImpl(LivreurRepository repository) {
        this.repository = repository;
    }

    @Override
    public void creerLivreur(Livreur livreur) {
        repository.save(livreur);
    }

    @Override
    public List<Livreur> listerLivreurs() {
        return repository.findAll();
    }
}
