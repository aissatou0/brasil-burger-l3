package sn.brasilburger.service.impl;

import sn.brasilburger.entity.Client;
import sn.brasilburger.repository.ClientRepository;
import sn.brasilburger.service.ClientService;

import java.util.List;
import java.util.Optional;

public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    // Injection par constructeur 
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client creerClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Optional<Client> rechercherParId(int id) {
        return clientRepository.findById(id);
    }

    @Override
    public Optional<Client> rechercherParEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public List<Client> listerClients() {
        return clientRepository.findAll();
    }

    @Override
    public boolean modifierClient(Client client) {
        return clientRepository.update(client);
    }

    @Override
    public boolean supprimerClient(int id) {
        return clientRepository.delete(id);
    }
}
