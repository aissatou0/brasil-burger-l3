<?php

namespace App\Repository;

use App\Entity\Commande;
use App\Entity\CommandeItem;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\ORM\Tools\Pagination\Paginator;
use Doctrine\Persistence\ManagerRegistry;
use Doctrine\ORM\EntityManagerInterface;

class CommandeRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Commande::class);
    }



    public function save(Commande $commande, bool $flush = true): void
    {
        $this->_em->persist($commande);

        if ($flush) {
            $this->_em->flush();
        }
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

public function findWithFilters(
    ?string $status,
    ?string $date,
    ?string $client
): array {
    $qb = $this->createQueryBuilder('c')
        ->leftJoin('c.client', 'cl')
        ->addSelect('cl')
        ->orderBy('c.dateCommande', 'DESC');

    if ($status) {
        $qb->andWhere('c.etatCommande = :status')
           ->setParameter('status', $status);
    }

    if ($date) {
        $qb->andWhere('DATE(c.dateCommande) = :date')
           ->setParameter('date', $date);
    }

    if ($client) {
        $qb->andWhere('cl.nom LIKE :client OR cl.prenom LIKE :client')
           ->setParameter('client', "%$client%");
    }

    return $qb->getQuery()->getResult();
}

public function getCommandeStats(): array
{
    $statuses = [
        'ALL' => null,
        'EN_ATTENTE' => 'EN_ATTENTE',
        'EN_PREPARATION' => 'EN_PREPARATION',
        'PRETE' => 'PRETE',
        'EN_LIVRAISON' => 'EN_LIVRAISON',
        'ANNULEE' => 'ANNULEE',
    ];

    $stats = [];

    foreach ($statuses as $key => $status) {
        $qb = $this->createQueryBuilder('c')
            ->select('COUNT(c.id)');

        if ($status) {
            $qb->where('c.etatCommande = :status')
               ->setParameter('status', $status);
        }

        $stats[$key] = (int) $qb->getQuery()->getSingleScalarResult();
    }

    return $stats;
}

public function findCommandesALivrer(): array
{
    return $this->createQueryBuilder('c')
        ->leftJoin('c.client', 'cl')
        ->leftJoin('c.livreur', 'l')
        ->leftJoin('c.zone', 'z')
        ->addSelect('cl', 'l', 'z')
        ->where('c.typeCommande = :type')
        ->andWhere('c.etatCommande IN (:etats)')
        ->setParameter('type', 'LIVRAISON')
        ->setParameter('etats', ['VALIDEE', 'EN_COURS'])
        ->orderBy('c.dateCommande', 'DESC')
        ->getQuery()
        ->getResult();
}
public function getDeliveryStats(): array
{
    $em = $this->getEntityManager();

    $todayStart = new \DateTime('today 00:00:00');
    $todayEnd   = new \DateTime('today 23:59:59');

    return [
        'pending' => (int) $em->createQuery(
            "SELECT COUNT(c.id)
             FROM App\Entity\Commande c
             WHERE c.typeCommande = 'LIVRAISON'
             AND c.etatCommande = 'VALIDEE'
             AND c.livreur IS NULL"
        )->getSingleScalarResult(),

        'progress' => (int) $em->createQuery(
            "SELECT COUNT(c.id)
             FROM App\Entity\Commande c
             WHERE c.typeCommande = 'LIVRAISON'
             AND c.etatCommande = 'EN_COURS'"
        )->getSingleScalarResult(),

        'done' => (int) $em->createQuery(
            "SELECT COUNT(c.id)
             FROM App\Entity\Commande c
             WHERE c.typeCommande = 'LIVRAISON'
             AND c.etatCommande = 'TERMINEE'
             AND c.dateCommande BETWEEN :start AND :end"
        )
        ->setParameter('start', $todayStart)
        ->setParameter('end', $todayEnd)
        ->getSingleScalarResult(),
    ];
}




}
