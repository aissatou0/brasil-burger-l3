<?php

namespace App\Controller\Admin;

use App\Repository\CommandeRepository;
use App\Repository\LivreurRepository;
use App\Repository\ZoneRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;
//use Doctrine\ORM\EntityManagerInterface;

#[Route('/admin/livraisons', name: 'admin_delivery_')]
class DeliveryController extends AbstractController
{
    #[Route('', name: 'index')]
    public function index(
        CommandeRepository $commandeRepo,
        LivreurRepository $livreurRepo,
        ZoneRepository $zoneRepo
    ): Response {
        return $this->render('admin/delivery/index.html.twig', [
            'commandes' => $commandeRepo->findCommandesALivrer(),
            'livreurs'  => $livreurRepo->findDisponibles(),
            'zones'     => $zoneRepo->findAll(),
            'stats'     => $commandeRepo->getDeliveryStats(),
        ]);
    }

    #[Route('/assigner', name: 'assign', methods: ['POST'])]
    public function assign(
        Request $request,
        CommandeRepository $commandeRepo,
        LivreurRepository $livreurRepo
    ): Response {
        $commande = $commandeRepo->find($request->request->get('commande_id'));
        $livreur  = $livreurRepo->find($request->request->get('livreur_id'));

        if (!$commande || !$livreur) {
            $this->addFlash('error', 'Commande ou livreur introuvable');
            return $this->redirectToRoute('admin_delivery_index');
        }

        $commande->setLivreur($livreur);
        $commande->setEtatCommande('EN_COURS');

        $commandeRepo->save($commande);

        return $this->redirectToRoute('admin_delivery_index');
    }
}
