<?php

namespace App\Entity;

use App\Repository\ClientRepository;
use Doctrine\ORM\Mapping as ORM;
use App\Entity\Gestionnaire;
use App\Entity\Commande;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
#[ORM\Entity(repositoryClass: ClientRepository::class)]
#[ORM\Table(name: 'clients')]
#[ORM\UniqueConstraint(name: 'uniq_client_email', columns: ['email'])]
#[ORM\UniqueConstraint(name: 'uniq_client_telephone', columns: ['telephone'])]

class Client
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 100)]
    private ?string $nom = null;

    #[ORM\Column(length: 150)]
    private ?string $prenom = null;

    #[ORM\Column(length: 30)]
    private ?string $telephone = null;

    #[ORM\Column(length: 150)]
    private ?string $email = null;

    #[ORM\Column(length: 150)]
    private ?string $password = null;

    #[ORM\ManyToOne(targetEntity: Gestionnaire::class)]
    #[ORM\JoinColumn(name: 'id_gestionnaire', referencedColumnName: 'id', nullable: true)]
    private ?Gestionnaire $gestionnaire = null;

    #[ORM\OneToMany(mappedBy: 'client', targetEntity: Commande::class)]
    private Collection $commandes;
    
    public function __construct(){
    $this->commandes = new ArrayCollection();
    }

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

    public function getTelephone(): ?string
    {
        return $this->telephone;
    }

    public function setTelephone(string $telephone): static
    {
        $this->telephone = $telephone;

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

    public function getPassword(): ?string
    {
        return $this->password;
    }

    public function setPassword(string $password): static
    {
        $this->password = $password;

        return $this;
    }

    public function getGestionnaire(): ?Gestionnaire
    {
        return $this->gestionnaire;
    }

    public function setGestionnaire(?Gestionnaire $gestionnaire): static
    {
        $this->gestionnaire = $gestionnaire;

        return $this;
    }
    public function getCommandes(): Collection
    {
        return $this->commandes;
    }
    public function addCommande(Commande $commande): static
    {
        if (!$this->commandes->contains($commande)) {
            $this->commandes->add($commande);
            $commande->setClient($this);
        }

        return $this;
    }


}
