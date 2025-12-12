package sn.brasilburger.view;

import sn.brasilburger.entity.Client;
import sn.brasilburger.entity.Commande;
import sn.brasilburger.entity.enums.EtatCommande;
import sn.brasilburger.entity.enums.TypeCommande;
import sn.brasilburger.entity.enums.TypeItem;
import sn.brasilburger.service.CommandeService;

import java.util.List;
import java.util.Scanner;

public class CommandeView {

    private final CommandeService commandeService;
    private final Scanner scanner = new Scanner(System.in);

    public CommandeView(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    public void demarrer() {
        int choix;
        do {
            nettoyerConsole();
            System.out.println("======================================");
            System.out.println("        GESTION DES COMMANDES");
            System.out.println("======================================");
            System.out.println("1 - Créer une commande");
            System.out.println("2 - Lister les commandes");
            System.out.println("3 - Changer l'état d'une commande");
            System.out.println("4 - Supprimer une commande");
            System.out.println("5 - Ajouter un item à une commande");
            System.out.println("0 - Retour");
            System.out.println("======================================");
            System.out.print("Votre choix : ");
            choix = saisirInt();

            switch (choix) {
                case 1 -> creerCommande();
                case 2 -> listerCommandes();
                case 3 -> changerEtat();
                case 4 -> supprimerCommande();
                case 5 -> ajouterItem();
                case 0 -> System.out.println("Retour...");
                default -> System.out.println("❌ Choix invalide.");
            }

            if (choix != 0) {
                pause();
            }

        } while (choix != 0);
    }

    private void creerCommande() {
        nettoyerConsole();
        System.out.println("=== CREATION COMMANDE ===");

        System.out.print("ID du client : ");
        int idClient = saisirInt();

        Commande commande = new Commande();

        Client client = new Client();
        client.setId(idClient);
        commande.setClient(client);

        System.out.println("Type de commande : ");
        System.out.println("1 - SUR PLACE");
        System.out.println("2 - A EMPORTER");
        System.out.println("3 - LIVRAISON");
        System.out.print("Choix : ");
        int choix = saisirInt();

        TypeCommande typeCommande;
        switch (choix) {
            case 1 -> typeCommande = TypeCommande.SUR_PLACE;
            case 2 -> typeCommande = TypeCommande.A_EMPORTER;
            case 3 -> typeCommande = TypeCommande.LIVRAISON;
            default -> {
                System.out.println("❌ Type invalide.");
                return;
            }
        }

        commande.setTypeCommande(typeCommande);

        commandeService.creerCommande(commande);
        System.out.println("✅ Commande créée avec succès !");
    }

    private void listerCommandes() {
        nettoyerConsole();
        System.out.println("=== LISTE DES COMMANDES ===");

        List<Commande> commandes = commandeService.listerCommandes();
        if (commandes.isEmpty()) {
            System.out.println("Aucune commande trouvée.");
            return;
        }

        commandes.forEach(System.out::println);
    }

    private void ajouterItem() {
        System.out.println("=== AJOUTER UN ITEM À UNE COMMANDE ===");

        System.out.print("ID de la commande : ");
        int idCommande = saisirInt();

        System.out.println("Type d'item : ");
        System.out.println("1 - BURGER");
        System.out.println("2 - COMPLEMENT");
        System.out.println("3 - MENU");
        System.out.print("Choix : ");
        int choixType = saisirInt();

        TypeItem typeItem;
        switch (choixType) {
            case 1 -> typeItem = TypeItem.BURGER;
            case 2 -> typeItem = TypeItem.COMPLEMENT;
            case 3 -> typeItem = TypeItem.MENU;
            default -> {
                System.out.println("❌ Type invalide");
                return;
            }
        }

        System.out.print("ID de l'item : ");
        int idItem = saisirInt();

        System.out.print("Quantité : ");
        int quantite = saisirInt();

        try {
            commandeService.ajouterItem(idCommande, typeItem, idItem, quantite);
            System.out.println("✅ Item ajouté à la commande !");
        } catch (Exception e) {
            System.out.println("❌ Erreur lors de l'ajout de l'item");
            e.printStackTrace();
        }
    }

    private void changerEtat() {
        nettoyerConsole();
        System.out.println("=== CHANGER ETAT COMMANDE ===");

        System.out.print("ID de la commande : ");
        int id = saisirInt();

        System.out.print("Nouvel état (EN_COURS, VALIDEE, ANNULEE, TERMINEE) : ");
        String saisie = scanner.nextLine().trim().toUpperCase();

        try {
            EtatCommande etat = EtatCommande.valueOf(saisie);
            commandeService.changerEtat(id, etat.name());
            System.out.println("✅ Etat mis à jour.");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Etat invalide.");
        }
    }

    private void supprimerCommande() {
        nettoyerConsole();
        System.out.println("=== SUPPRESSION COMMANDE ===");

        System.out.print("ID de la commande : ");
        int id = saisirInt();

        commandeService.supprimerCommande(id);
        System.out.println("✅ Commande supprimée.");
    }

    private int saisirInt() {
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("❌ Saisir un nombre valide : ");
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // consomme le \n qui reste
        return value;
    }

    private void pause() {
        System.out.println("\nAppuyez sur Entrée pour continuer...");
        scanner.nextLine();
    }

    private void nettoyerConsole() {
        for (int i = 0; i < 30; i++) System.out.println();
    }
}
