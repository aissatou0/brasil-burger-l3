<?php

namespace App\Entity;

use App\Repository\MenuItemRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: MenuItemRepository::class)]
#[ORM\Table(
    name: 'menu_items',
    uniqueConstraints: [
        new ORM\UniqueConstraint(
            name: 'unique_menu_item',
            columns: ['id_menu', 'type_item', 'id_item']
        )
    ]
)]
class MenuItem
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\ManyToOne(inversedBy: 'menuItems')]
    #[ORM\JoinColumn(name: 'id_menu', nullable: false, onDelete: 'CASCADE')]
    private ?Menu $menu = null;

    #[ORM\Column(name: 'type_item', length: 20)]
    private string $typeItem;

    #[ORM\Column(name: 'id_item')]
    private int $itemId;

    #[ORM\Column(options: ['default' => 1])]
    private int $quantite = 1;
    public function getId(): ?int
    {
        return $this->id;
    }

    public function getMenu(): ?Menu
    {
        return $this->menu;
    }

    public function setMenu(?Menu $menu): static
    {
        $this->menu = $menu;

        return $this;
    }

    public function getTypeItem(): ?string
    {
        return $this->typeItem;
    }

    public function setTypeItem(string $typeItem): static
    {
        $this->typeItem = $typeItem;

        return $this;
    }

    public function getItemId(): ?int
    {
        return $this->itemId;
    }

    public function setItemId(int $itemId): static
    {
        $this->itemId = $itemId;

        return $this;
    }

    public function getQuantite(): ?int
    {
        return $this->quantite;
    }

    public function setQuantite(?int $quantite): static
    {
        $this->quantite = $quantite;

        return $this;
    }
    
}
