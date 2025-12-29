<?php

namespace App\Controller\Admin;

use App\Entity\Burger;
use App\Repository\BurgerRepository;
use Cloudinary\Cloudinary;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\RedirectResponse;
#[Route('/admin/produits', name: 'admin_burgers_')]
class BurgerController extends AbstractController
{
    #[Route('', name: 'index', methods: ['GET'])]
    public function index(BurgerRepository $burgerRepository): Response
    {
        return $this->render('admin/produit/index.html.twig', [
            'burgers' => $burgerRepository->findAll()
        ]);
    }

    #[Route('/add', name: 'add', methods: ['POST'])]
    public function add(Request $request, EntityManagerInterface $em): Response
    {
        $nom  = trim($request->request->get('nom'));
        $prix = $request->request->get('prix');
        $file = $request->files->get('image');

        if (!$nom || !$prix || !$file) {
            $this->addFlash('danger', 'Tous les champs sont obligatoires');
            return $this->redirectToRoute('admin_burgers_index');
        }

        $cloudinary = new Cloudinary($_ENV['CLOUDINARY_URL']);

        $uploadResult = $cloudinary->uploadApi()->upload(
            $file->getRealPath(),
            [
                'folder' => 'brasil-burger/burgers',
                'public_id' => uniqid('burger_'),
                'options' => ['verify' => false]
            ]
        );

        $burger = new Burger();
        $burger->setNom($nom);
        $burger->setPrix($prix);
        $burger->setImage($uploadResult['secure_url']);
        $burger->setActif(true);

        $em->persist($burger);
        $em->flush();

        $this->addFlash('success', 'Burger ajouté avec succès');

        return $this->redirectToRoute('admin_burgers_index');
    }

    

#[Route('/{id}/archive', name: 'archive', methods: ['POST'])]
public function archive(
    Burger $burger,
    EntityManagerInterface $em
): RedirectResponse {
    $burger->setActif(false);
    $em->flush();

    $this->addFlash('success', 'Burger archivé avec succès');

    return $this->redirectToRoute('admin_burgers_index');
}

#[Route('/{id}/reactiver', name: 'reactiver', methods: ['POST'])]
public function reactiver(
    Burger $burger,
    EntityManagerInterface $em
): RedirectResponse {
    $burger->setActif(true);
    $em->flush();

    $this->addFlash('success', 'Burger réactivé avec succès');

    return $this->redirectToRoute('admin_burgers_index');
}

}
