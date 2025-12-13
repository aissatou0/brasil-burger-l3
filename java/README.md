# 🍔 BRASIL BURGER – Application de gestion

Application console Java de gestion d’un restaurant (permet l'ajout de fixtures dans toutes les tables de la base de donnees commune du projet Brasil Burger)(clients, commandes, burgers, compléments, menus, paiements, livreurs, zones,...).

---

## 🛠️ Technologies utilisées

- Java 21
- Maven
- PostgreSQL (Neon)
- Cloudinary (images)
- Architecture MVC + Repository + Service (SOLID)

---

## 📦 Prérequis

- Java JDK 21
- Maven 3.9+
- Connexion Internet (Neon + Cloudinary)

Vérification :
```bash
java -version
mvn -version

## ▶️ Exécution du projet

##Depuis la racine du projet :
mvn clean compile
mvn exec:java -Dexec.mainClass="sn.brasilburger.Main"

🗄️ Base de données

Base PostgreSQL hébergée sur Neon

Tables : clients, commandes, commande_items, burgers, compléments, menus, menu_items, paiements, gestionnaires, livreurs, zones

⚠️ Remarques importantes

L’application est une application console

Le lancement se fait via Maven (pas via un IDE)

Les images sont stockées sur Cloudinary

Les identifiants de base sont fournis à des fins pédagogiques
