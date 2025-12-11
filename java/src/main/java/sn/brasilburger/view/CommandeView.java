package sn.brasilburger.view;

import sn.brasilburger.entity.Client;
import sn.brasilburger.entity.Commande;
import sn.brasilburger.entity.enums.TypeCommande;
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
            System.out.println("0 - Retour");
            System.out.println("======================================");
            System.out.print("Votre choix : ");
            choix = saisirInt();

            switch (choix) {
                case 1 -> creerCommande();
                case 2 -> listerCommandes();
                case 3 -> changerEtat();
                case 4 -> supprimerCommande();
                case 0 -> System.out.println("Retour...");
                default -> System.out.println("❌ Choix invalide.");
            }

            pause();

        } while (choix != 0);
    }

private void creerCommande() {
    nettoyerConsole();
    System.out.println("=== CREATION COMMANDE ===");

    System.out.print("ID du client : ");
    int idClient = saisirInt();

    Commande commande = new Commande();

    // On ne charge QUE l'ID (optimisation SOLID)
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
            pause();
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

    private void changerEtat() {
        nettoyerConsole();
        System.out.println("=== CHANGER ETAT COMMANDE ===");

        System.out.print("ID de la commande : ");
        int id = saisirInt();

        System.out.print("Nouvel état (EN_COURS, VALIDE, TERMINE, ANNULE) : ");
        String etat = scanner.nextLine().toUpperCase();

        commandeService.changerEtat(id, etat);
        System.out.println("✅ Etat mis à jour.");
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
        return scanner.nextInt();
    }

    private void pause() {
        System.out.println("\nAppuyez sur Entrée pour continuer...");
        scanner.nextLine();
        scanner.nextLine();
    }

    private void nettoyerConsole() {
        for (int i = 0; i < 30; i++) System.out.println();
    }
}
