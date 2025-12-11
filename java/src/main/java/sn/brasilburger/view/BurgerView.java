package sn.brasilburger.view;

import sn.brasilburger.entity.Burger;
import sn.brasilburger.service.BurgerService;

import java.util.List;
import java.util.Scanner;

public class BurgerView {

    private final BurgerService burgerService;
    private final Scanner scanner = new Scanner(System.in);

    public BurgerView(BurgerService burgerService) {
        this.burgerService = burgerService;
    }

    public void demarrer() {
        int choix;

        do {
            nettoyerConsole();
            System.out.println("======================================");
            System.out.println("        GESTION DES BURGERS");
            System.out.println("======================================");
            System.out.println("1 - Ajouter un burger");
            System.out.println("2 - Lister les burgers");
            System.out.println("3 - Supprimer un burger");
            System.out.println("0 - Retour");
            System.out.println("======================================");
            System.out.print("Votre choix : ");

            while (!scanner.hasNextInt()) {
                scanner.next();
                System.out.print("❌ Saisir un nombre valide : ");
            }
            choix = scanner.nextInt();
            scanner.nextLine(); // consommer le retour chariot

            switch (choix) {
                case 1 -> ajouterBurger();
                case 2 -> listerBurgers();
                case 3 -> supprimerBurger();
                case 0 -> System.out.println("↩ Retour au menu principal...");
                default -> {
                    System.out.println("❌ Choix invalide.");
                    pause();
                }
            }

        } while (choix != 0);
    }

    private void ajouterBurger() {
        try {
            System.out.println("=== AJOUT D'UN BURGER ===");

            System.out.print("Nom : ");
            String nom = scanner.nextLine();

            System.out.print("Prix : ");
            double prix = scanner.nextDouble();
            scanner.nextLine(); // consommer

            System.out.print("Chemin local de l'image (ou vide pour aucune) : ");
            String imagePath = scanner.nextLine();

            Burger burger = burgerService.creerBurger(nom, prix, imagePath.isBlank() ? null : imagePath);
            System.out.println("✅ Burger créé : " + burger);

        } catch (Exception e) {
            System.out.println("❌ Erreur : " + e.getMessage());
        }
        pause();
    }

    private void listerBurgers() {
        System.out.println("=== LISTE DES BURGERS ===");
        List<Burger> burgers = burgerService.listerBurgers();

        if (burgers.isEmpty()) {
            System.out.println("Aucun burger trouvé.");
        } else {
            burgers.forEach(System.out::println);
        }
        pause();
    }

    private void supprimerBurger() {
        System.out.println("=== SUPPRESSION BURGER ===");
        System.out.print("ID du burger : ");

        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("❌ Saisir un nombre valide : ");
        }
        int id = scanner.nextInt();
        scanner.nextLine();

        boolean ok = burgerService.supprimerBurger(id);
        if (ok) {
            System.out.println("✅ Burger supprimé.");
        } else {
            System.out.println("❌ Échec de la suppression (ID introuvable ?).");
        }
        pause();
    }

    private void nettoyerConsole() {
        for (int i = 0; i < 40; i++) System.out.println();
    }

    private void pause() {
        System.out.println("\nAppuyez sur Entrée pour continuer...");
        scanner.nextLine();
    }
}
