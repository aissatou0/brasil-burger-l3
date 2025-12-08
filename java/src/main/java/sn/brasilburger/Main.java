package sn.brasilburger;

import sn.brasilburger.repository.ClientRepository;
import sn.brasilburger.repository.impl.ClientRepositoryImpl;
import sn.brasilburger.service.ClientService;
import sn.brasilburger.service.impl.ClientServiceImpl;
import sn.brasilburger.view.ClientView;

public class Main {
    public static void main(String[] args) {

        ClientRepository clientRepository = new ClientRepositoryImpl();
        ClientService clientService = new ClientServiceImpl(clientRepository);
        ClientView clientView = new ClientView(clientService);

        clientView.demarrer();
    }
}
