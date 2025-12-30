<?php

namespace App\Controller\Admin;

use App\Repository\ClientRepository;
use App\Entity\Client;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/admin/clients', name: 'admin_customers_')]
class CustomerController extends AbstractController
{
    /**
     * 📋 LISTE DES CLIENTS (recherche + filtres + pagination)
     * URL : /admin/clients
     * Route : admin_customers_index
     */
    #[Route('', name: 'index', methods: ['GET'])]
    public function index(
        Request $request,
        ClientRepository $clientRepository
    ): Response {

        // 🔍 Recherche & filtre
        $search = $request->query->get('q');
        $filter = $request->query->get('filter');

        // 📄 Pagination
        $page  = max(1, (int) $request->query->get('page', 1));
        $limit = 10;

        // 🔥 Données paginées avec stats
        $result = $clientRepository->findWithStatsPaginated(
            $page,
            $limit,
            $search,
            $filter
        );

        $clients = [];

        foreach ($result['data'] as $row) {
            $client = $row[0];
            $client->nbCommandes  = (int) $row['nbCommandes'];
            $client->totalDepense = (float) $row['totalDepense'];
            $clients[] = $client;
        }

        $totalPages = (int) ceil($result['total'] / $limit);

        // 📊 Statistiques globales
        $stats = $clientRepository->getClientStats();

        return $this->render('admin/customer/index.html.twig', [
            'clients'     => $clients,
            'stats'       => $stats,
            'search'      => $search,
            'filter'      => $filter,
            'page'        => $page,
            'totalPages'  => $totalPages,
        ]);
    }

    /**
     * 🛍️ COMMANDES D’UN CLIENT
     * URL : /admin/clients/{id}/commandes
     * Route : admin_customers_orders
     */
    #[Route('/{id}/commandes', name: 'orders', methods: ['GET'])]
    public function orders(
        Client $client
    ): Response {
        return $this->render('admin/customer/orders.html.twig', [
            'client'    => $client,
            'commandes' => $client->getCommandes(),
        ]);
    }

    /**
     * 👁️ MODAL CLIENT (AJAX)
     * URL : /admin/clients/{id}/modal
     * Route : admin_customers_modal
     */
    #[Route('/{id}/modal', name: 'modal', methods: ['GET'])]
    public function modal(
        Client $client
    ): Response {
        return $this->render('admin/customer/_modal.html.twig', [
            'client' => $client,
        ]);
    }
}
