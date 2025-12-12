package sn.brasilburger.view;

import sn.brasilburger.entity.Gestionnaire;
import sn.brasilburger.service.GestionnaireService;

import java.util.Scanner;

public class GestionnaireView {

    private final GestionnaireService service;
    private final Scanner scanner = new Scanner(System.in);

    public GestionnaireView(GestionnaireService service) {
        this.service = service;
    }

    public void demarrer() {
        int choix;
        do {
            System.out.println("=== GESTION DES GESTIONNAIRES ===");
            System.out.println("1 - Créer un gestionnaire");
            System.out.println("2 - Lister les gestionnaires");
            System.out.println("0 - Retour");
            System.out.print("Choix : ");
            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 -> creer();
                case 2 -> lister();
            }

        } while (choix != 0);
    }

    private void creer() {
        Gestionnaire g = new Gestionnaire();

        System.out.print("Nom : ");
        g.setNom(scanner.nextLine());

        System.out.print("Prénom : ");
        g.setPrenom(scanner.nextLine());

        System.out.print("Email : ");
        g.setEmail(scanner.nextLine());

        System.out.print("Téléphone : ");
        g.setTelephone(scanner.nextLine());

        System.out.print("Mot de passe : ");
        g.setPassword(scanner.nextLine());

        service.creerGestionnaire(g);
        System.out.println("✅ Gestionnaire créé !");
    }

    private void lister() {
        service.listerGestionnaires().forEach(System.out::println);
    }
}
