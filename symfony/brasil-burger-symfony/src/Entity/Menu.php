<?php

namespace App\Entity;

use App\Repository\MenuRepository;
use Doctrine\ORM\Mapping as ORM;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use App\Entity\MenuItem;
use App\Entity\Gestionnaire;
#[ORM\Entity(repositoryClass: MenuRepository::class)]
#[ORM\Table(name: 'menus')]

class Menu
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 150)]
    private ?string $nom = null;

    #[ORM\Column(length: 255, nullable: true)]
    private ?string $image = null;

    #[ORM\Column(options: ['default' => true])]
    private ?bool $actif = true;

    #[ORM\OneToMany(mappedBy: 'menu', targetEntity: MenuItem::class)]
    private Collection $menuItems;

    public function __construct()
{
    $this->menuItems = new ArrayCollection();
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

    public function getImage(): ?string
    {
        return $this->image;
    }

    public function setImage(?string $image): static
    {
        $this->image = $image;

        return $this;
    }

    public function isActif(): ?bool
    {
        return $this->actif;
    }

    public function setActif(?bool $actif): static
    {
        $this->actif = $actif;

        return $this;
    }
    public function getMenuItems(): Collection
    {
        return $this->menuItems;
    }
    public function addMenuItem(MenuItem $menuItem): static
    {
        if (!$this->menuItems->contains($menuItem)) {
            $this->menuItems->add($menuItem);
            $menuItem->setMenu($this);
        }

        return $this;
    }
    
}
