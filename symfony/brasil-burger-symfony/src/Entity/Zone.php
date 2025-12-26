<?php

namespace App\Entity;

use App\Repository\ZoneRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: ZoneRepository::class)]
#[ORM\Table(name: 'zones')]

class Zone
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 150)]
    private ?string $nom = null;

    #[ORM\Column(type: Types::DECIMAL, precision: 10, scale: 2)]
    private ?string $prixLivraison = null;

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

    public function getPrixLivraison(): ?string
    {
        return $this->prixLivraison;
    }

    public function setPrixLivraison(string $prixLivraison): static
    {
        $this->prixLivraison = $prixLivraison;

        return $this;
    }
    
}
