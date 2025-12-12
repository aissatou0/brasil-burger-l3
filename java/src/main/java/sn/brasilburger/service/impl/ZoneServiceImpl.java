package sn.brasilburger.service.impl;

import sn.brasilburger.entity.Zone;
import sn.brasilburger.repository.ZoneRepository;
import sn.brasilburger.service.ZoneService;

import java.util.List;

public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository repository;

    public ZoneServiceImpl(ZoneRepository repository) {
        this.repository = repository;
    }

    @Override
    public void creerZone(Zone zone) {
        repository.save(zone);
    }

    @Override
    public List<Zone> listerZones() {
        return repository.findAll();
    }

    @Override
    public Zone getById(int id) {
        return repository.findById(id);
    }
}
