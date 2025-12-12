package sn.brasilburger.repository;

import sn.brasilburger.entity.Complement;

import java.util.List;

public interface ComplementRepository {

    void save(Complement complement);

    List<Complement> findAll();

    void deleteById(int id);
    double findPrixById(int idComplement);

}

