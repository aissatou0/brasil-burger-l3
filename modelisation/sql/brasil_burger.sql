-- ============================================================
-- DATABASE : BRASIL BURGER (PostgreSQL)
-- BASE COMMUNE AUX 3 PROJETS (JAVA / C# / SYMFONY)
-- ============================================================

CREATE DATABASE brasil_burger;
\c brasil_burger;

-- ============================================================
-- TABLE : GESTIONNAIRES (Admin Symfony)
-- ============================================================
CREATE TABLE gestionnaires (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(150) NOT NULL,
    prenom VARCHAR(150) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    actif BOOLEAN DEFAULT TRUE
);

-- ============================================================
-- TABLE : CLIENTS (Application mobile / C#)
-- ============================================================
CREATE TABLE clients (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    telephone VARCHAR(30) UNIQUE NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- ============================================================
-- TABLE : BURGERS
-- ============================================================
CREATE TABLE burgers (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(150) NOT NULL,
    prix NUMERIC(10,2) NOT NULL CHECK (prix > 0),
    image VARCHAR(255),
    actif BOOLEAN DEFAULT TRUE
);

-- ============================================================
-- TABLE : COMPLEMENTS (Boisson / Frites)
-- ============================================================
CREATE TABLE complements (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(150) NOT NULL,
    prix NUMERIC(10,2) NOT NULL CHECK (prix > 0),
    type_complement VARCHAR(20) NOT NULL CHECK (type_complement IN ('BOISSON', 'FRITES')),
    image VARCHAR(255),
    actif BOOLEAN DEFAULT TRUE
);

-- ============================================================
-- TABLE : MENUS (Composition)
-- ============================================================
CREATE TABLE menus (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(150) NOT NULL,
    image VARCHAR(255),
    actif BOOLEAN DEFAULT TRUE
);

-- Table de composition Menu → items (burger + boisson + frites)
CREATE TABLE menu_items (
    id SERIAL PRIMARY KEY,
    id_menu INT REFERENCES menus(id) ON DELETE CASCADE,
    type_item VARCHAR(20) NOT NULL CHECK (type_item IN ('BURGER', 'COMPLEMENT')),
    id_item INT NOT NULL,
    quantite INT DEFAULT 1,
    UNIQUE(id_menu, type_item, id_item)
);

-- ============================================================
-- TABLE : ZONES (livraison)
-- ============================================================
CREATE TABLE zones (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(150) NOT NULL,
    prix_livraison NUMERIC(10,2) NOT NULL CHECK (prix_livraison >= 0)
);

-- ============================================================
-- TABLE : LIVREURS
-- ============================================================
CREATE TABLE livreurs (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(150) NOT NULL,
    telephone VARCHAR(30) UNIQUE NOT NULL
);

-- ============================================================
-- TABLE : COMMANDES
-- ============================================================
CREATE TABLE commandes (
    id SERIAL PRIMARY KEY,
    id_client INT REFERENCES clients(id),
    type_commande VARCHAR(20) NOT NULL CHECK (type_commande IN ('SUR_PLACE', 'A_EMPORTER', 'LIVRAISON')),
    etat VARCHAR(20) NOT NULL DEFAULT 'EN_COURS'
        CHECK (etat IN ('EN_COURS','VALIDE','TERMINE','ANNULE')),
    date_commande TIMESTAMP DEFAULT NOW(),
    total NUMERIC(10,2),
    id_zone INT REFERENCES zones(id),
    id_livreur INT REFERENCES livreurs(id)
);

-- ============================================================
-- TABLE : COMMANDE ITEMS (détail commande)
-- ============================================================
CREATE TABLE commande_items (
    id SERIAL PRIMARY KEY,
    id_commande INT REFERENCES commandes(id) ON DELETE CASCADE,
    type_item VARCHAR(20) NOT NULL CHECK (type_item IN ('BURGER','MENU','COMPLEMENT')),
    id_item INT NOT NULL,
    quantite INT DEFAULT 1,
    prix NUMERIC(10,2) NOT NULL CHECK (prix >= 0)
);

-- ============================================================
-- TABLE : PAIEMENTS
-- ============================================================
CREATE TABLE paiements (
    id SERIAL PRIMARY KEY,
    id_commande INT UNIQUE REFERENCES commandes(id) ON DELETE CASCADE,
    date_paiement TIMESTAMP DEFAULT NOW(),
    montant NUMERIC(10,2) NOT NULL,
    mode VARCHAR(10) NOT NULL CHECK (mode IN ('WAVE','OM'))
);
