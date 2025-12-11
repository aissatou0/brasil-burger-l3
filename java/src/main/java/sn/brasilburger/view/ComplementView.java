package sn.brasilburger.view;

import sn.brasilburger.entity.Complement;
import sn.brasilburger.entity.enums.TypeComplement;
import sn.brasilburger.service.ComplementService;

import java.util.List;
import java.util.Scanner;

public class ComplementView {

    private final ComplementService complementService;
    private final Scanner scanner = new Scanner(System.in);

    public ComplementView(ComplementService complementService) {
        this.complementService = complementService;
    }

    public void demarrer() {
        int choix;
        do {
            nettoyerConsole();
            System.out.println("======================================");
            System.out.println("       GESTION DES COMPLEMENTS");
            System.out.println("======================================");
            System.out.println("1 - Ajouter un complément");
            System.out.println("2 - Lister les compléments");
            System.out.println("3 - Supprimer un complément");
            System.out.println("0 - Retour");
            System.out.println("======================================");
            System.out.print("Votre choix : ");

            while (!scanner.hasNextInt()) {
                scanner.next();
                System.out.print("❌ Choix invalide : ");
            }

            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 -> ajouterComplement();
                case 2 -> listerComplements();
                case 3 -> supprimerComplement();
                case 0 -> {}
                default -> pause();
            }

        } while (choix != 0);
    }

    // ===================== ACTIONS =====================

    private void ajouterComplement() {
        try {
            System.out.println("\n=== AJOUT COMPLEMENT ===");

            System.out.print("Nom : ");
            String nom = scanner.nextLine();

            System.out.print("Prix : ");
            double prix = scanner.nextDouble();
            scanner.nextLine();

            System.out.println("Type :");
            System.out.println("1 - BOISSON");
            System.out.println("2 - FRITES");
            System.out.print("Choix : ");
            int choixType = scanner.nextInt();
            scanner.nextLine();

            TypeComplement type =
                    choixType == 1 ? TypeComplement.BOISSON : TypeComplement.FRITES;

            System.out.print("Chemin image (optionnel) : ");
            String imagePath = scanner.nextLine();

            complementService.ajouterComplement(nom, prix, type, imagePath);

            System.out.println("✅ Complément ajouté avec succès !");
            pause();

        } catch (Exception e) {
            System.out.println("❌ Erreur : " + e.getMessage());
            pause();
        }
    }

    private void listerComplements() {
        System.out.println("\n=== LISTE DES COMPLEMENTS ===");
        List<Complement> complements = complementService.listerComplements();

        if (complements.isEmpty()) {
            System.out.println("Aucun complément trouvé.");
        } else {
            complements.forEach(System.out::println);
        }
        pause();
    }

    private void supprimerComplement() {
        try {
            System.out.print("\nID du complément à supprimer : ");
            int id = scanner.nextInt();
            scanner.nextLine();

            complementService.supprimerComplement(id);
            System.out.println("✅ Complément supprimé.");
        } catch (Exception e) {
            System.out.println("❌ Erreur : " + e.getMessage());
        }
        pause();
    }

    // ===================== UTILS =====================

    private void nettoyerConsole() {
        for (int i = 0; i < 30; i++) System.out.println();
    }

    private void pause() {
        System.out.println("\nAppuyez sur Entrée pour continuer...");
        scanner.nextLine();
    }
}
