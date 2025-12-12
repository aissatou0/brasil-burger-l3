package sn.brasilburger;

import sn.brasilburger.config.CloudinaryConfig;

import sn.brasilburger.repository.*;
import sn.brasilburger.repository.impl.*;

import sn.brasilburger.service.*;
import sn.brasilburger.service.impl.*;

import sn.brasilburger.view.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // ==============================
        // 1️⃣ CONFIGURATION
        // ==============================
        CloudinaryConfig.init(
                "dchvgy1gt",
                "616118585995331",
                "138EBUTN_Sh9Kle-og_2xiYlTkM"
        );
        System.out.println("☁️ Cloudinary configuré !");

        ImageStorageService imageStorageService = new CloudinaryImageStorageService();

        // ==============================
        // 2️⃣ REPOSITORIES
        // ==============================
        ClientRepository clientRepository = new ClientRepositoryImpl();
        CommandeRepository commandeRepository = new CommandeRepositoryImpl();
        CommandeItemRepository commandeItemRepository = new CommandeItemRepositoryImpl();

        BurgerRepository burgerRepository = new BurgerRepositoryImpl();
        ComplementRepository complementRepository = new ComplementRepositoryImpl();
        MenuRepository menuRepository = new MenuRepositoryImpl();

        PaiementRepository paiementRepository = new PaiementRepositoryImpl();
        GestionnaireRepository gestionnaireRepository = new GestionnaireRepositoryImpl();

        // ==============================
        // 3️⃣ SERVICES
        // ==============================
        ClientService clientService = new ClientServiceImpl(clientRepository);

        CommandeService commandeService = new CommandeServiceImpl(
                commandeRepository,
                commandeItemRepository,
                burgerRepository,
                complementRepository,
                menuRepository
        );

        PaiementService paiementService = new PaiementServiceImpl(
                paiementRepository,
                commandeRepository,
                commandeItemRepository
        );

        BurgerService burgerService =
                new BurgerServiceImpl(burgerRepository, imageStorageService);

        ComplementService complementService =
                new ComplementServiceImpl(complementRepository, imageStorageService);

        MenuService menuService =
                new MenuServiceImpl(menuRepository, imageStorageService);
        GestionnaireService gestionnaireService = new GestionnaireServiceImpl(gestionnaireRepository);
        // ==============================
        // 4️⃣ VUES
        // ==============================
        ClientView clientView = new ClientView(clientService);
        CommandeView commandeView = new CommandeView(commandeService, paiementService);
        BurgerView burgerView = new BurgerView(burgerService);
        ComplementView complementView = new ComplementView(complementService);
        MenuView menuView = new MenuView(menuService);
        GestionnaireView gestionnaireView = new GestionnaireView(gestionnaireService);

        


        // ==============================
        // 5️⃣ MENU PRINCIPAL
        // ==============================
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
            System.out.println("6 - Gestion des Gestionnaires");
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
                case 6 -> gestionnaireView.demarrer();

                case 0 -> System.out.println("✅ Fermeture de l'application...");
                default -> pause(scanner);
            }

        } while (choix != 0);
    }

    private static void nettoyerConsole() {
        for (int i = 0; i < 40; i++) System.out.println();
    }

    private static void pause(Scanner scanner) {
        System.out.println("\nAppuyez sur Entrée pour continuer...");
        scanner.nextLine();
        scanner.nextLine();
    }
}
