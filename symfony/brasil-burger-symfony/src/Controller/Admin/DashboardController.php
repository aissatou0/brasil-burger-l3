<?php

namespace App\Controller\Admin;

use App\Repository\CommandeRepository;
use App\Repository\BurgerRepository;
use App\Repository\MenuRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/admin/dashboard', name: 'admin_dashboard')]
class DashboardController extends AbstractController
{
    public function __invoke(
        Request $request,
        CommandeRepository $commandeRepo,
        BurgerRepository $burgerRepo,
        MenuRepository $menuRepo
    ): Response {
        // --------------------
        // Paramètres
        // --------------------
        $period = $request->query->get('period', 'day'); // day|week|month
        $q = trim((string) $request->query->get('q', ''));
        $page = max(1, (int) $request->query->get('page', 1));

        $now = new \DateTimeImmutable();

        // --------------------
        // Fenêtre temporelle
        // --------------------
        $from = match ($period) {
            'week'  => $now->modify('-7 days'),
            'month' => $now->modify('-30 days'),
            default => $now->setTime(0, 0, 0),
        };

        $to = $now;

        // --------------------
        // Statistiques globales
        // --------------------
        $stats = $commandeRepo->getDashboardStats($from, $to);

        // --------------------
        // Commandes récentes
        // --------------------
        $recent = $commandeRepo->getRecentCommandesPaginated($page, 10, $q);

        // --------------------
        // Top produits (RAW)
        // --------------------
        $topRaw = $commandeRepo->getTopProduitsRaw($from, $to, 5);

        $topProduits = [];
        foreach ($topRaw as $idx => $row) {
            $type = strtoupper((string) ($row['type'] ?? ''));
            $itemId = (int) ($row['itemId'] ?? 0);

            $nom = 'Produit inconnu';
            $image = null;

            if ($type === 'BURGER') {
                $burger = $burgerRepo->find($itemId);
                if ($burger) {
                    $nom = $burger->getNom();
                    $image = $burger->getImage();
                }
            } elseif ($type === 'MENU') {
                $menu = $menuRepo->find($itemId);
                if ($menu) {
                    $nom = $menu->getNom();
                    $image = $menu->getImage();
                }
            }

            $topProduits[] = [
                'rank'  => $idx + 1,
                'type'  => $type,
                'badge'=> $type,
                'nom'   => $nom,
                'image'=> $image,
                'total'=> (int) ($row['total'] ?? 0),
            ];
        }

        // --------------------
        // Graphique évolution des ventes
        // --------------------
        $salesEvolution = $commandeRepo->getSalesEvolution($from, $to, $period);

        $salesLabels = [];
        $salesData = [];

        foreach ($salesEvolution as $row) {
            $salesLabels[] = $row['label'];
            $salesData[]   = (float) $row['total'];
        }

        // --------------------
        // Render
        // --------------------
        return $this->render('admin/dashboard/index.html.twig', [
            'stats'         => $stats,
            'recent'        => $recent,
            'topProduits'   => $topProduits,
            'period'        => $period,
            'q'             => $q,
            'gestionnaire'  => $this->getUser(),
            'salesLabels'   => $salesLabels,
            'salesData'     => $salesData,
        ]);
    }
}
