package sn.brasilburger.view;

import sn.brasilburger.entity.Gestionnaire;
import sn.brasilburger.entity.Livreur;
import sn.brasilburger.entity.Zone;
import sn.brasilburger.service.LivreurService;

import java.util.Scanner;

public class LivreurView {

    private final LivreurService service;
    private final Scanner scanner = new Scanner(System.in);

    public LivreurView(LivreurService service) {
        this.service = service;
    }

    public void demarrer() {
        int choix;
        do {
            System.out.println("=== GESTION DES LIVREURS ===");
            System.out.println("1 - Créer un livreur");
            System.out.println("2 - Lister les livreurs");
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
        Livreur l = new Livreur();

        System.out.print("Nom : ");
        l.setNom(scanner.nextLine());

        System.out.print("Téléphone : ");
        l.setTelephone(scanner.nextLine());

        System.out.print("ID Gestionnaire : ");
        Gestionnaire g = new Gestionnaire();
        g.setId(scanner.nextInt());

        System.out.print("ID Zone : ");
        Zone z = new Zone();
        z.setId(scanner.nextInt());
        scanner.nextLine();

        l.setGestionnaire(g);
        l.setZone(z);

        service.creerLivreur(l);
        System.out.println("✅ Livreur créé !");
    }

    private void lister() {
        service.listerLivreurs().forEach(System.out::println);
    }
}
