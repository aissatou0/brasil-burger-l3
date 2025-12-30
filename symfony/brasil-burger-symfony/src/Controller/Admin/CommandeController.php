<?php

namespace App\Controller\Admin;

use App\Entity\Commande;
use App\Repository\CommandeRepository;
use App\Repository\BurgerRepository;
use App\Repository\MenuRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/admin/commandes', name: 'admin_commandes_')]
class CommandeController extends AbstractController
{
    /**
     * 📋 LISTE DES COMMANDES
     */
    #[Route('', name: 'index', methods: ['GET'])]
    public function index(
        Request $request,
        CommandeRepository $commandeRepository
    ): Response {
        $status = $request->query->get('status');
        $date   = $request->query->get('date');
        $client = $request->query->get('client');

        $commandes = $commandeRepository->findWithFilters(
            status: $status,
            date: $date,
            client: $client
        );

        $stats = $commandeRepository->getCommandeStats();

        return $this->render('admin/commande/index.html.twig', [
            'commandes' => $commandes,
            'stats' => $stats,
            'filters' => [
                'status' => $status,
                'date' => $date,
                'client' => $client,
            ]
        ]);
    }

    /**
     * 🔍 DÉTAIL D’UNE COMMANDE
     */
    #[Route('/{id}', name: 'show', requirements: ['id' => '\d+'], methods: ['GET'])]
    public function show(
        Commande $commande,
        BurgerRepository $burgerRepository,
        MenuRepository $menuRepository
    ): Response {
        $itemsDetails = [];

        foreach ($commande->getItems() as $item) {
            $type   = strtoupper($item->getTypeItem());
            $itemId = $item->getItemId();

            $nom   = $type . ' #' . $itemId;
            $image = null;

            if ($type === 'BURGER') {
                if ($burger = $burgerRepository->find($itemId)) {
                    $nom   = $burger->getNom();
                    $image = $burger->getImage();
                }
            }

            if ($type === 'MENU') {
                if ($menu = $menuRepository->find($itemId)) {
                    $nom   = $menu->getNom();
                    $image = $menu->getImage();
                }
            }

            $itemsDetails[] = [
                'typeItem' => $type,
                'itemId'   => $itemId,
                'nom'      => $nom,
                'image'    => $image,
                'prix'     => (float) $item->getPrix(),
                'quantite' => $item->getQuantite(),
            ];
        }

        return $this->render('admin/commande/show.html.twig', [
            'commande'     => $commande,
            'itemsDetails' => $itemsDetails,
        ]);
    }

    /**
     * 🔥 PASSER EN PRÉPARATION
     */
    #[Route('/{id}/preparer', name: 'prepare', methods: ['POST'])]
    public function preparer(
        Commande $commande,
        EntityManagerInterface $em
    ): RedirectResponse {
        if ($commande->getEtatCommande() !== 'EN_COURS') {
            $this->addFlash('danger', 'Action non autorisée.');
            return $this->redirectToRoute('admin_commandes_show', ['id' => $commande->getId()]);
        }

        $commande->setEtatCommande('VALIDEE');
        $em->flush();

        $this->addFlash('success', 'Commande passée en préparation.');
        return $this->redirectToRoute('admin_commandes_show', ['id' => $commande->getId()]);
    }

    /**
     * 🚫 ANNULER
     */
    #[Route('/{id}/annuler', name: 'cancel', methods: ['POST'])]
    public function annuler(
        Commande $commande,
        EntityManagerInterface $em
    ): RedirectResponse {
        if ($commande->getEtatCommande() !== 'EN_COURS') {
            $this->addFlash('danger', 'Impossible d’annuler cette commande.');
            return $this->redirectToRoute('admin_commandes_show', ['id' => $commande->getId()]);
        }

        $commande->setEtatCommande('ANNULEE');
        $em->flush();

        $this->addFlash('success', 'Commande annulée.');
        return $this->redirectToRoute('admin_commandes_show', ['id' => $commande->getId()]);
    }

    /**
     * 🚚 LANCER LIVRAISON / TERMINER
     */
    #[Route('/{id}/livrer', name: 'deliver', methods: ['POST'])]
    public function livrer(
        Commande $commande,
        EntityManagerInterface $em
    ): RedirectResponse {
        if ($commande->getEtatCommande() !== 'VALIDEE') {
            $this->addFlash('danger', 'Action non autorisée.');
            return $this->redirectToRoute('admin_commandes_show', ['id' => $commande->getId()]);
        }

        $commande->setEtatCommande('TERMINEE');
        $em->flush();

        $this->addFlash('success', 'Commande livrée.');
        return $this->redirectToRoute('admin_commandes_show', ['id' => $commande->getId()]);
    }

    
}
