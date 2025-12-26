<?php

namespace App\Entity;

use App\Repository\CommandeRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use App\Entity\Client;
use App\Entity\Gestionnaire;
use App\Entity\Livreur;
use App\Entity\Zone;
use App\Entity\CommandeItem;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
#[ORM\Entity(repositoryClass: CommandeRepository::class)]
#[ORM\Table(name: 'commandes')]
class Commande
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\ManyToOne]
    #[ORM\JoinColumn(name: 'id_client')]
    private ?Client $client = null;

    #[ORM\ManyToOne]
    #[ORM\JoinColumn(name: 'id_gestionnaire')]
    private ?Gestionnaire $gestionnaire = null;

    #[ORM\ManyToOne]
    #[ORM\JoinColumn(name: 'id_livreur')]
    private ?Livreur $livreur = null;

    #[ORM\ManyToOne]
    #[ORM\JoinColumn(name: 'id_zone')]
    private ?Zone $zone = null;

    #[ORM\Column(name: 'date_commande', type: 'datetime')]
    private ?\DateTimeInterface $dateCommande = null;

    #[ORM\Column(type: Types::DECIMAL, precision: 10, scale: 2, nullable: true)]
    private ?string $total = null;

    #[ORM\Column(length: 20, name: 'etat_commande', nullable: true)]
    private ?string $etatCommande = null;

    #[ORM\Column(length: 20, name: 'type_commande', nullable: true)]
    private ?string $typeCommande = null;

    #[ORM\OneToMany(mappedBy: 'commande', targetEntity: CommandeItem::class, orphanRemoval: true)]
    private Collection $items;

    #[ORM\OneToOne(mappedBy: 'commande', targetEntity: Paiement::class, cascade: ['persist', 'remove'])]
    private ?Paiement $paiement = null;


    public function __construct(){
        $this->items = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getClient(): ?Client
    {
        return $this->client;
    }

    public function setClient(?Client $client): static
    {
        $this->client = $client;

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

    public function getLivreur(): ?Livreur
    {
        return $this->livreur;
    }

    public function setLivreur(?Livreur $livreur): static
    {
        $this->livreur = $livreur;

        return $this;
    }

    public function getZone(): ?Zone
    {
        return $this->zone;
    }

    public function setZone(?Zone $zone): static
    {
        $this->zone = $zone;

        return $this;
    }

    public function getDateCommande(): ?\DateTime
    {
        return $this->dateCommande;
    }

    public function setDateCommande(?\DateTime $dateCommande): static
    {
        $this->dateCommande = $dateCommande;

        return $this;
    }

    public function getTotal(): ?string
    {
        return $this->total;
    }

    public function setTotal(?string $total): static
    {
        $this->total = $total;

        return $this;
    }

    public function getEtatCommande(): ?string
    {
        return $this->etatCommande;
    }

    public function setEtatCommande(?string $etatCommande): static
    {
        $this->etatCommande = $etatCommande;

        return $this;
    }

    public function getTypeCommande(): ?string
    {
        return $this->typeCommande;
    }

    public function setTypeCommande(?string $typeCommande): static
    {
        $this->typeCommande = $typeCommande;

        return $this;
    }
    public function getPaiement(): ?Paiement
    {
        return $this->paiement;
    }
    public function setPaiement(Paiement $paiement): static
    {
        // set the owning side of the relation if necessary
        if ($paiement->getCommande() !== $this) {
            $paiement->setCommande($this);
        }

        $this->paiement = $paiement;

        return $this;
    }

    public function getItems(): Collection
    {
        return $this->items;
    }
    public function addItem(CommandeItem $item): static
    {
        if (!$this->items->contains($item)) {
            $this->items->add($item);
            $item->setCommande($this);
        }

        return $this;
    }
}
