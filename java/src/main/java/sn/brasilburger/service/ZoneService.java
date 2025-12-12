package sn.brasilburger.service;

import sn.brasilburger.entity.Zone;
import java.util.List;

public interface ZoneService {

    void creerZone(Zone zone);

    List<Zone> listerZones();

    Zone getById(int id);
}
