package sn.brasilburger.repository;

import sn.brasilburger.entity.Zone;
import java.util.List;

public interface ZoneRepository {

    void save(Zone zone);

    List<Zone> findAll();

    Zone findById(int id);
}
