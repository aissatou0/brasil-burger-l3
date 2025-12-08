package sn.brasilburger.repository;

import sn.brasilburger.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {

    Client save(Client client);

    Optional<Client> findById(int id);

    Optional<Client> findByEmail(String email);

    List<Client> findAll();

    boolean update(Client client);

    boolean delete(int id);
}

