package sn.brasilburger.view;

import sn.brasilburger.entity.Menu;
import sn.brasilburger.entity.enums.TypeItem;
import sn.brasilburger.service.MenuService;

import java.util.List;
import java.util.Scanner;

public class MenuView {

    private final MenuService menuService;
    private final Scanner scanner = new Scanner(System.in);

    public MenuView(MenuService menuService) {
        this.menuService = menuService;
    }

    public void demarrer() {
        int choix;
        do {
            nettoyerConsole();
            System.out.println("======================================");
            System.out.println("        GESTION DES MENUS");
            System.out.println("======================================");
            System.out.println("1 - Créer un menu");
            System.out.println("2 - Ajouter un item au menu");
            System.out.println("3 - Lister les menus");
            System.out.println("4 - Supprimer un menu");
            System.out.println("0 - Retour");
            System.out.println("======================================");
            System.out.print("Votre choix : ");

            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 -> creerMenu();
                case 2 -> ajouterItem();
                case 3 -> listerMenus();
                case 4 -> supprimerMenu();
                case 0 -> System.out.println("↩️ Retour menu principal");
                default -> pause();
            }

        } while (choix != 0);
    }

    // ==========================
    private void creerMenu() {
        System.out.println("=== CRÉATION MENU ===");
        System.out.print("Nom : ");
        String nom = scanner.nextLine();

        System.out.print("Chemin image (optionnel) : ");
        String image = scanner.nextLine();

        menuService.creerMenu(nom, image);
        pause();
    }

    private void ajouterItem() {
        System.out.println("=== AJOUT ITEM AU MENU ===");

        System.out.print("ID du menu : ");
        int menuId = scanner.nextInt();

        System.out.println("Type d'item :");
        System.out.println("1 - BURGER");
        System.out.println("2 - COMPLEMENT");
        int type = scanner.nextInt();

        TypeItem typeItem = (type == 1) ? TypeItem.BURGER : TypeItem.COMPLEMENT;

        System.out.print("ID de l'item : ");
        int idItem = scanner.nextInt();

        System.out.print("Quantité : ");
        int quantite = scanner.nextInt();

        menuService.ajouterItem(menuId, typeItem, idItem, quantite);
        pause();
    }

    private void listerMenus() {
        System.out.println("=== LISTE DES MENUS ===");
        List<Menu> menus = menuService.listerMenus();

        if (menus.isEmpty()) {
            System.out.println("Aucun menu trouvé.");
        } else {
            menus.forEach(System.out::println);
        }
        pause();
    }

    private void supprimerMenu() {
        System.out.print("ID du menu à supprimer : ");
        int id = scanner.nextInt();

        menuService.supprimerMenu(id);
        pause();
    }

    // ==========================
    private void nettoyerConsole() {
        for (int i = 0; i < 40; i++) System.out.println();
    }

    private void pause() {
        System.out.println("\nAppuyez sur Entrée pour continuer...");
        scanner.nextLine();
    }
}
