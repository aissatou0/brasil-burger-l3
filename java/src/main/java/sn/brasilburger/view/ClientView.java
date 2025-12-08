package sn.brasilburger.view;

import sn.brasilburger.config.Validator;
import sn.brasilburger.entity.Client;
import sn.brasilburger.service.ClientService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ClientView {

    private final ClientService clientService;
    private final Scanner scanner = new Scanner(System.in);

    public ClientView(ClientService clientService) {
        this.clientService = clientService;
    }

    public void demarrer() {
        boolean continuer = true;

        while (continuer) {
            clearConsole();
            afficherMenu();

            System.out.print("\nVotre choix : ");
            String choix = scanner.nextLine().trim();

            switch (choix) {
                case "1" -> creerClient();
                case "2" -> listerClients();
                case "3" -> modifierClient();
                case "4" -> supprimerClient();
                case "0" -> {
                    System.out.println("\nAu revoir 👋");
                    continuer = false;
                }
                default -> {
                    System.out.println("\n❌ Choix invalide.");
                    pause();
                }
            }
        }
    }

    private void afficherMenu() {
        System.out.println("======================================");
        System.out.println("        GESTION DES CLIENTS");
        System.out.println("======================================");
        System.out.println("1 - Ajouter un client");
        System.out.println("2 - Lister les clients");
        System.out.println("3 - Modifier un client");
        System.out.println("4 - Supprimer un client");
        System.out.println("0 - Quitter");
        System.out.println("======================================");
    }

    // ============ ACTIONS ============

    private void creerClient() {
        clearConsole();
        System.out.println("=== AJOUT D'UN NOUVEAU CLIENT ===");

        String nom = saisirNonVide("Nom : ");
        String prenom = saisirNonVide("Prénom : ");
        String telephone = saisirTelephoneSn();
        String email = saisirEmail();
        String password = saisirNonVide("Mot de passe : ");

        Client client = new Client(nom, prenom, telephone, email, password);
        Client saved = clientService.creerClient(client);

        if (saved != null && saved.getId() > 0) {
            System.out.println("\n✅ Client enregistré avec succès ! (ID = " + saved.getId() + ")");
        } else {
            System.out.println("\n❌ Erreur lors de l'enregistrement du client.");
        }

        pause();
    }

    private void listerClients() {
        clearConsole();
        System.out.println("=== LISTE DES CLIENTS ===");

        List<Client> clients = clientService.listerClients();

        if (clients.isEmpty()) {
            System.out.println("\nAucun client trouvé.");
        } else {
            for (Client c : clients) {
                System.out.println("- ID: " + c.getId()
                        + " | " + c.getNom() + " " + c.getPrenom()
                        + " | Tel: " + c.getTelephone()
                        + " | Email: " + c.getEmail());
            }
        }

        pause();
    }

    private void modifierClient() {
        clearConsole();
        System.out.println("=== MODIFICATION D'UN CLIENT ===");

        int id = saisirEntier("ID du client à modifier : ");
        Optional<Client> optClient = clientService.rechercherParId(id);

        if (optClient.isEmpty()) {
            System.out.println("\n❌ Aucun client trouvé avec cet ID.");
            pause();
            return;
        }

        Client client = optClient.get();
        System.out.println("\nClient actuel : " + client);

        String nom = saisirOptionnel("Nouveau nom (laisser vide pour garder actuel) : ");
        if (!nom.isEmpty()) client.setNom(nom);

        String prenom = saisirOptionnel("Nouveau prénom (laisser vide pour garder actuel) : ");
        if (!prenom.isEmpty()) client.setPrenom(prenom);

        String tel = saisirOptionnel("Nouveau téléphone (laisser vide pour garder actuel) : ");
        if (!tel.isEmpty()) {
            while (!Validator.isValidPhoneSn(tel)) {
                System.out.println("❌ Téléphone invalide. Format attendu : 70/71/75/76/77/78 + 7 chiffres (ex : 771234567)");
                tel = saisirOptionnel("Nouveau téléphone (laisser vide pour garder actuel) : ");
                if (tel.isEmpty()) break;
            }
            if (!tel.isEmpty()) client.setTelephone(tel.trim());
        }

        String email = saisirOptionnel("Nouvel email (laisser vide pour garder actuel) : ");
        if (!email.isEmpty()) {
            while (!Validator.isValidEmail(email)) {
                System.out.println("❌ Email invalide.");
                email = saisirOptionnel("Nouvel email (laisser vide pour garder actuel) : ");
                if (email.isEmpty()) break;
            }
            if (!email.isEmpty()) client.setEmail(email.trim());
        }

        String password = saisirOptionnel("Nouveau mot de passe (laisser vide pour garder actuel) : ");
        if (!password.isEmpty()) client.setPassword(password.trim());

        boolean ok = clientService.modifierClient(client);
        if (ok) {
            System.out.println("\n✅ Client modifié avec succès.");
        } else {
            System.out.println("\n❌ Erreur lors de la modification du client.");
        }

        pause();
    }

    private void supprimerClient() {
        clearConsole();
        System.out.println("=== SUPPRESSION D'UN CLIENT ===");

        int id = saisirEntier("ID du client à supprimer : ");
        boolean ok = clientService.supprimerClient(id);

        if (ok) {
            System.out.println("\n✅ Client supprimé avec succès.");
        } else {
            System.out.println("\n❌ Aucun client trouvé avec cet ID ou erreur lors de la suppression.");
        }

        pause();
    }

    // ============ MÉTHODES UTILITAIRES ============

    private String saisirNonVide(String message) {
        String value;
        do {
            System.out.print(message);
            value = scanner.nextLine().trim();
            if (!Validator.isNonEmpty(value)) {
                System.out.println("❌ Ce champ est obligatoire.");
            }
        } while (!Validator.isNonEmpty(value));
        return value;
    }

    private String saisirOptionnel(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    private int saisirEntier(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Veuillez saisir un nombre entier valide.");
            }
        }
    }

    private String saisirTelephoneSn() {
        String tel;
        do {
            System.out.print("Téléphone (format SN, ex: 771234567) : ");
            tel = scanner.nextLine().trim().replaceAll("\\s+", "");
            if (!Validator.isValidPhoneSn(tel)) {
                System.out.println("❌ Téléphone invalide. Doit commencer par 70, 71, 75, 76, 77 ou 78 et contenir 9 chiffres.");
            }
        } while (!Validator.isValidPhoneSn(tel));
        return tel;
    }

    private String saisirEmail() {
        String email;
        do {
            System.out.print("Email : ");
            email = scanner.nextLine().trim();
            if (!Validator.isValidEmail(email)) {
                System.out.println("❌ Email invalide. Veuillez réessayer.");
            }
        } while (!Validator.isValidEmail(email));
        return email;
    }

    private void pause() {
        System.out.println("\nAppuyez sur Entrée pour continuer...");
        scanner.nextLine();
    }

    private void clearConsole() {
        try {
            // Effacement "propre" sur la plupart des consoles
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } catch (Exception e) {
            // fallback : on imprime des lignes vides
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
}
