package sn.brasilburger.service;

import sn.brasilburger.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    Client creerClient(Client client);

    Optional<Client> rechercherParId(int id);

    Optional<Client> rechercherParEmail(String email);

    List<Client> listerClients();

    boolean modifierClient(Client client);

    boolean supprimerClient(int id);
}
