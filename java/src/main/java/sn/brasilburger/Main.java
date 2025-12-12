package sn.brasilburger;

import sn.brasilburger.config.CloudinaryConfig;
import sn.brasilburger.repository.ClientRepository;
import sn.brasilburger.repository.CommandeItemRepository;
import sn.brasilburger.repository.CommandeRepository;
import sn.brasilburger.repository.BurgerRepository;
import sn.brasilburger.repository.impl.ClientRepositoryImpl;
import sn.brasilburger.repository.impl.CommandeItemRepositoryImpl;
import sn.brasilburger.repository.impl.CommandeRepositoryImpl;
import sn.brasilburger.repository.impl.BurgerRepositoryImpl;
import sn.brasilburger.service.ClientService;
import sn.brasilburger.service.CommandeService;
import sn.brasilburger.service.BurgerService;
import sn.brasilburger.service.ImageStorageService;
import sn.brasilburger.service.MenuService;
import sn.brasilburger.service.impl.ClientServiceImpl;
import sn.brasilburger.service.impl.CommandeServiceImpl;
import sn.brasilburger.service.impl.BurgerServiceImpl;
import sn.brasilburger.service.impl.CloudinaryImageStorageService;
import sn.brasilburger.view.ClientView;
import sn.brasilburger.view.CommandeView;
import sn.brasilburger.view.BurgerView;
import sn.brasilburger.repository.ComplementRepository;
import sn.brasilburger.repository.MenuRepository;
import sn.brasilburger.repository.impl.ComplementRepositoryImpl;
import sn.brasilburger.repository.impl.MenuRepositoryImpl;
import sn.brasilburger.service.ComplementService;
import sn.brasilburger.service.impl.ComplementServiceImpl;
import sn.brasilburger.service.impl.MenuServiceImpl;
import sn.brasilburger.view.ComplementView;
import sn.brasilburger.view.MenuView;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Initialisation Cloudinary
        CloudinaryConfig.init(
                "dchvgy1gt",              // cloud name
                "616118585995331",        // api key
                "138EBUTN_Sh9Kle-og_2xiYlTkM" // api secret
        );
        System.out.println("☁️ Cloudinary configuré !");

        // Service de stockage d'images
        ImageStorageService imageStorageService = new CloudinaryImageStorageService();

        // Repositories
        ClientRepository clientRepository = new ClientRepositoryImpl();
        CommandeRepository commandeRepository = new CommandeRepositoryImpl();
        BurgerRepository burgerRepository = new BurgerRepositoryImpl();
        ComplementRepository complementRepository = new ComplementRepositoryImpl();
        MenuRepository menuRepository = new MenuRepositoryImpl();

        // Services
        ClientService clientService = new ClientServiceImpl(clientRepository);
        //CommandeService commandeService = new CommandeServiceImpl(commandeRepository);
        CommandeItemRepository commandeItemRepository = new CommandeItemRepositoryImpl();

CommandeService commandeService = new CommandeServiceImpl(
        commandeRepository,
        commandeItemRepository,
        burgerRepository,
        complementRepository,
        menuRepository
);

        BurgerService burgerService = new BurgerServiceImpl(burgerRepository, imageStorageService);
        ComplementService complementService = new ComplementServiceImpl(complementRepository, imageStorageService);
        MenuService menuService = new MenuServiceImpl(menuRepository, imageStorageService);

        // Vues
        ClientView clientView = new ClientView(clientService);
        CommandeView commandeView = new CommandeView(commandeService);
        BurgerView burgerView = new BurgerView(burgerService);
        ComplementView complementView = new ComplementView(complementService);
        MenuView menuView = new MenuView(menuService);

        
        
        
        


        Scanner scanner = new Scanner(System.in);
        int choix;

        do {
            nettoyerConsole();
            System.out.println("==========================================");
            System.out.println("     BRASIL BURGER - GESTION PRINCIPALE");
            System.out.println("==========================================");
            System.out.println("1 - Gestion des Clients");
            System.out.println("2 - Gestion des Commandes");
            System.out.println("3 - Gestion des Burgers");
            System.out.println("4 - Gestion des Compléments");
            System.out.println("5 - Gestion des Menus");

            System.out.println("0 - Quitter");
            System.out.println("==========================================");
            System.out.print("Votre choix : ");

            while (!scanner.hasNextInt()) {
                scanner.next();
                System.out.print("❌ Saisir un nombre valide : ");
            }
            choix = scanner.nextInt();

            switch (choix) {
                case 1 -> clientView.demarrer();
                case 2 -> commandeView.demarrer();
                case 3 -> burgerView.demarrer();
                case 4 -> complementView.demarrer();
                case 5 -> menuView.demarrer();
                case 0 -> System.out.println("✅ Fermeture de l'application...");
                default -> {
                    System.out.println("❌ Choix invalide.");
                    pause(scanner);
                }
            }

        } while (choix != 0);
    }

    private static void nettoyerConsole() {
        for (int i = 0; i < 40; i++) System.out.println();
    }

    private static void pause(Scanner scanner) {
        System.out.println("\nAppuyez sur Entrée pour continuer...");
        scanner.nextLine(); // consomme le \n qui traîne
        scanner.nextLine(); // attend l'appui sur Entrée
    }
}
