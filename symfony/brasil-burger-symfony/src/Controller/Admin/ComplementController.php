<?php

namespace App\Controller\Admin;

use App\Entity\Complement;
use App\Repository\ComplementRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Cloudinary\Cloudinary;

#[Route('/admin/complements', name: 'admin_complements_')]
class ComplementController extends AbstractController
{
    #[Route('', name: 'index', methods: ['GET'])]
    public function index(ComplementRepository $repository): Response
    {
        return $this->render('admin/complement/index.html.twig', [
            'complements' => $repository->findAll()
        ]);
    }

    #[Route('/add', name: 'add', methods: ['POST'])]
    public function add(
        Request $request,
        EntityManagerInterface $em
    ): Response {
        $name  = trim($request->request->get('name'));
        $prix  = $request->request->get('prix');
        $type  = $request->request->get('typeComplement');
        $file  = $request->files->get('image');

        if (!$name || !$prix || !$type || !$file) {
            $this->addFlash('danger', 'Tous les champs sont obligatoires');
            return $this->redirectToRoute('admin_complements_index');
        }

        // ☁️ Cloudinary
        $cloudinary = new Cloudinary($_ENV['CLOUDINARY_URL']);

        $upload = $cloudinary->uploadApi()->upload(
            $file->getRealPath(),
            [
                'folder' => 'brasil-burger/complements',
                'public_id' => uniqid('complement_'),
                'resource_type' => 'image'
            ]
        );

        $complement = new Complement();
        $complement->setName($name);
        $complement->setPrix($prix);
        $complement->setTypeComplement($type);
        $complement->setImage($upload['secure_url']);
        $complement->setActif(true);

        $em->persist($complement);
        $em->flush();

        $this->addFlash('success', 'Complément ajouté avec succès');

        return $this->redirectToRoute('admin_complements_index');
    }
    #[Route('/{id}/toggle', name: 'toggle', methods: ['POST'])]
public function toggle(
    Complement $complement,
    EntityManagerInterface $em
): Response {
    $complement->setActif(!$complement->isActif());
    $em->flush();

    return $this->redirectToRoute('admin_complements_index');
}

#[Route('/{id}/edit', name: 'edit', methods: ['POST'])]
public function edit(
    Complement $complement,
    Request $request,
    EntityManagerInterface $em
): Response {
    $name  = trim($request->request->get('name'));
    $prix  = $request->request->get('prix');
    $type  = $request->request->get('typeComplement');
    $file  = $request->files->get('image');

    if (!$name || !$prix || !$type) {
        $this->addFlash('danger', 'Champs obligatoires manquants');
        return $this->redirectToRoute('admin_complements_index');
    }

    $complement->setName($name);
    $complement->setPrix($prix);
    $complement->setTypeComplement($type);

    // 🔄 Image facultative
    if ($file) {
        $cloudinary = new Cloudinary($_ENV['CLOUDINARY_URL']);

        $upload = $cloudinary->uploadApi()->upload(
            $file->getRealPath(),
            [
                'folder' => 'brasil-burger/complements',
                'public_id' => uniqid('complement_'),
                'resource_type' => 'image'
            ]
        );

        $complement->setImage($upload['secure_url']);
    }

    $em->flush();

    $this->addFlash('success', 'Complément modifié avec succès');

    return $this->redirectToRoute('admin_complements_index');
}

}