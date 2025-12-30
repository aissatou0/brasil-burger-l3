<?php

namespace App\Repository;

use App\Entity\Client;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;
use Doctrine\ORM\Tools\Pagination\Paginator;
/**
 * @extends ServiceEntityRepository<Client>
 */
class ClientRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Client::class);
    }
    public function findWithStats(?string $search = null, ?string $filter = null): array
{
    $qb = $this->createQueryBuilder('c')
        ->select('c')
        ->addSelect('COUNT(cmd.id) AS nbCommandes')
        ->addSelect('COALESCE(SUM(cmd.total), 0) AS totalDepense')
        ->leftJoin('c.commandes', 'cmd')
        ->groupBy('c.id');

    // 🔍 Recherche client
    if ($search) {
        $qb->andWhere('LOWER(c.nom) LIKE :q OR LOWER(c.prenom) LIKE :q OR LOWER(c.email) LIKE :q')
           ->setParameter('q', '%' . strtolower($search) . '%');
    }

    // 🎯 Filtres statut
    if ($filter === 'vip') {
        $qb->having('COUNT(cmd.id) >= 20');
    }

    if ($filter === 'new') {
        $qb->having('COUNT(cmd.id) < 5');
    }

    if ($filter === 'regular') {
        $qb->having('COUNT(cmd.id) BETWEEN 5 AND 19');
    }

    return $qb->getQuery()->getResult();
}



public function getClientStats(): array
{
    $em = $this->getEntityManager();

    $totalClients = (int) $em->createQuery(
        'SELECT COUNT(c.id) FROM App\Entity\Client c'
    )->getSingleScalarResult();

    $vipClients = (int) $em->createQuery(
        'SELECT COUNT(c.id)
         FROM App\Entity\Client c
         WHERE (
            SELECT COUNT(cmd.id)
            FROM App\Entity\Commande cmd
            WHERE cmd.client = c
         ) >= 20'
    )->getSingleScalarResult();

    $nouveauxClients = (int) $em->createQuery(
        'SELECT COUNT(c.id)
         FROM App\Entity\Client c
         WHERE (
            SELECT MIN(cmd.dateCommande)
            FROM App\Entity\Commande cmd
            WHERE cmd.client = c
         ) >= :date'
    )
    ->setParameter('date', new \DateTime('-30 days'))
    ->getSingleScalarResult();

    return [
        'total' => $totalClients,
        'vip' => $vipClients,
        'nouveaux' => $nouveauxClients,
    ];
}



public function findWithStatsPaginated(
    int $page,
    int $limit,
    ?string $search = null,
    ?string $filter = null
): array {
    $qb = $this->createQueryBuilder('c')
        ->select('c')
        ->addSelect('COUNT(cmd.id) AS nbCommandes')
        ->addSelect('COALESCE(SUM(cmd.total), 0) AS totalDepense')
        ->leftJoin('c.commandes', 'cmd')
        ->groupBy('c.id')
        ->setFirstResult(($page - 1) * $limit)
        ->setMaxResults($limit);

    if ($search) {
        $qb->andWhere('LOWER(c.nom) LIKE :q OR LOWER(c.prenom) LIKE :q OR LOWER(c.email) LIKE :q')
           ->setParameter('q', '%' . strtolower($search) . '%');
    }

    if ($filter === 'vip') {
        $qb->having('COUNT(cmd.id) >= 20');
    } elseif ($filter === 'new') {
        $qb->having('COUNT(cmd.id) < 5');
    } elseif ($filter === 'regular') {
        $qb->having('COUNT(cmd.id) BETWEEN 5 AND 19');
    }

    $paginator = new Paginator($qb->getQuery());

    return [
        'data' => iterator_to_array($paginator),
        'total' => count($paginator)
    ];
}







    //    /**
    //     * @return Client[] Returns an array of Client objects
    //     */
    //    public function findByExampleField($value): array
    //    {
    //        return $this->createQueryBuilder('c')
    //            ->andWhere('c.exampleField = :val')
    //            ->setParameter('val', $value)
    //            ->orderBy('c.id', 'ASC')
    //            ->setMaxResults(10)
    //            ->getQuery()
    //            ->getResult()
    //        ;
    //    }

    //    public function findOneBySomeField($value): ?Client
    //    {
    //        return $this->createQueryBuilder('c')
    //            ->andWhere('c.exampleField = :val')
    //            ->setParameter('val', $value)
    //            ->getQuery()
    //            ->getOneOrNullResult()
    //        ;
    //    }
}
