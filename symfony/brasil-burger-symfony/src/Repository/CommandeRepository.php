<?php

namespace App\Repository;

use App\Entity\Commande;
use App\Entity\CommandeItem;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\ORM\Tools\Pagination\Paginator;
use Doctrine\Persistence\ManagerRegistry;

class CommandeRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Commande::class);
    }

    /**
     * =========================
     * DASHBOARD STATS
     * =========================
     */
    public function getDashboardStats(\DateTimeImmutable $from, \DateTimeImmutable $to): array
    {
        // Commandes du jour
        $commandesDuJour = (int) $this->createQueryBuilder('c')
            ->select('COUNT(c.id)')
            ->where('c.dateCommande BETWEEN :from AND :to')
            ->setParameter('from', $from)
            ->setParameter('to', $to)
            ->getQuery()
            ->getSingleScalarResult();

        // Commandes en cours
        $enCours = (int) $this->createQueryBuilder('c')
            ->select('COUNT(c.id)')
            ->where('c.etatCommande = :etat')
            ->setParameter('etat', 'EN_COURS')
            ->getQuery()
            ->getSingleScalarResult();

        // Recette du jour
        $recetteDuJour = (float) $this->createQueryBuilder('c')
            ->select('COALESCE(SUM(c.total), 0)')
            ->where('c.dateCommande BETWEEN :from AND :to')
            ->setParameter('from', $from)
            ->setParameter('to', $to)
            ->getQuery()
            ->getSingleScalarResult();

        // Commandes annulées
        $annulations = (int) $this->createQueryBuilder('c')
            ->select('COUNT(c.id)')
            ->where('c.etatCommande = :etat')
            ->setParameter('etat', 'ANNULEE')
            ->getQuery()
            ->getSingleScalarResult();

        return [
            'commandesDuJour' => $commandesDuJour,
            'enCours' => $enCours,
            'recetteDuJour' => $recetteDuJour,
            'annulations' => $annulations,
        ];
    }

    /**
     * =========================
     * COMMANDES RÉCENTES PAGINÉES
     * =========================
     */
    public function getRecentCommandesPaginated(
        int $page,
        int $limit
    ): array {
        $page = max(1, $page);
        $limit = max(1, min(50, $limit));
        $offset = ($page - 1) * $limit;

        $qb = $this->createQueryBuilder('c')
            ->orderBy('c.dateCommande', 'DESC')
            ->setFirstResult($offset)
            ->setMaxResults($limit);

        $paginator = new Paginator($qb->getQuery());
        $total = count($paginator);
        $pages = (int) ceil($total / $limit);

        return [
            'items' => iterator_to_array($paginator),
            'total' => $total,
            'page' => $page,
            'pages' => $pages,
            'limit' => $limit,
        ];
    }

    /**
     * =========================
     * TOP PRODUITS (SANS JOIN POLYMORPHIQUE)
     * =========================
     */
    public function getTopProduitsRaw(
        \DateTimeImmutable $from,
        \DateTimeImmutable $to,
        int $limit = 5
    ): array {
        $qb = $this->getEntityManager()->createQueryBuilder()
            ->select('ci.typeItem AS type, ci.itemId AS itemId, SUM(ci.quantite) AS total')
            ->from(CommandeItem::class, 'ci')
            ->join('ci.commande', 'c')
            ->where('c.dateCommande BETWEEN :from AND :to')
            ->setParameter('from', $from)
            ->setParameter('to', $to)
            ->groupBy('ci.typeItem, ci.itemId')
            ->orderBy('total', 'DESC')
            ->setMaxResults($limit);

        return $qb->getQuery()->getArrayResult();
    }

    public function getSalesEvolution(
    \DateTimeImmutable $from,
    \DateTimeImmutable $to,
    string $period = 'day'
): array
{
    $qb = $this->createQueryBuilder('c')
        ->select('c.dateCommande AS date', 'SUM(c.total) AS total')
        ->andWhere('c.dateCommande BETWEEN :from AND :to')
        ->andWhere('c.etatCommande = :etat')
        ->setParameter('from', $from)
        ->setParameter('to', $to)
        ->setParameter('etat', 'TERMINEE')
        ->groupBy('date')
        ->orderBy('date', 'ASC');

    $rows = $qb->getQuery()->getResult();

    $data = [];

    foreach ($rows as $row) {
        /** @var \DateTimeInterface $date */
        $date = $row['date'];

        $label = match ($period) {
            'week', 'day' => $date->format('d/m'),
            'month' => $date->format('d/m'),
            default => $date->format('d/m'),
        };

        $data[] = [
            'label' => $label,
            'total' => (float) $row['total'],
        ];
    }

    return $data;
}


}
