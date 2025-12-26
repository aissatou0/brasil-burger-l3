<?php

namespace App\Entity;

use App\Repository\GestionnaireRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Security\Core\User\PasswordAuthenticatedUserInterface;
use Symfony\Component\Security\Core\User\UserInterface;

#[ORM\Entity(repositoryClass: GestionnaireRepository::class)]
#[ORM\Table(name: 'gestionnaires')]
#[ORM\UniqueConstraint(name: 'uniq_gestionnaire_email', columns: ['email'])]
class Gestionnaire implements UserInterface, PasswordAuthenticatedUserInterface
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 150)]
    private ?string $nom = null;

    #[ORM\Column(length: 150)]
    private ?string $prenom = null;

    #[ORM\Column(length: 150, unique: true)]
    private ?string $email = null;

    #[ORM\Column(length: 30, nullable: true)]
    private ?string $telephone = null;

    #[ORM\Column(length: 255)]
    private ?string $password = null;

    // ----------------------------
    // Getters/Setters
    // ----------------------------
    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNom(): ?string
    {
        return $this->nom;
    }

    public function setNom(string $nom): static
    {
        $this->nom = $nom;
        return $this;
    }

    public function getPrenom(): ?string
    {
        return $this->prenom;
    }

    public function setPrenom(string $prenom): static
    {
        $this->prenom = $prenom;
        return $this;
    }

    public function getEmail(): ?string
    {
        return $this->email;
    }

    public function setEmail(string $email): static
    {
        $this->email = $email;
        return $this;
    }

    public function getTelephone(): ?string
    {
        return $this->telephone;
    }

    public function setTelephone(?string $telephone): static
    {
        $this->telephone = $telephone;
        return $this;
    }

    public function getPassword(): ?string
    {
        return $this->password;
    }

    public function setPassword(string $password): static
    {
        $this->password = $password;
        return $this;
    }

    // ----------------------------
    // Security (UserInterface)
    // ----------------------------
    public function getUserIdentifier(): string
    {
        return $this->email;
    }

    public function getRoles(): array
    {
        return ['ROLE_GESTIONNAIRE'];
    }

    public function eraseCredentials(): void {}
}
