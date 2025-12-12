package sn.brasilburger.view;

import sn.brasilburger.entity.Zone;
import sn.brasilburger.service.ZoneService;

import java.util.Scanner;

public class ZoneView {

    private final ZoneService service;
    private final Scanner scanner = new Scanner(System.in);

    public ZoneView(ZoneService service) {
        this.service = service;
    }

    public void demarrer() {
        int choix;
        do {
            System.out.println("=== GESTION DES ZONES ===");
            System.out.println("1 - Créer une zone");
            System.out.println("2 - Lister les zones");
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
        Zone z = new Zone();

        System.out.print("Nom de la zone : ");
        z.setNom(scanner.nextLine());

        System.out.print("Prix de livraison : ");
        z.setPrixLivraison(scanner.nextDouble());
        scanner.nextLine();

        service.creerZone(z);
        System.out.println("✅ Zone créée !");
    }

    private void lister() {
        service.listerZones().forEach(System.out::println);
    }
}
