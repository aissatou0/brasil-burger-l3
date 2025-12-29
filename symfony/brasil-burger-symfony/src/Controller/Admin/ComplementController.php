<?php

namespace App\Controller\Admin;

use App\Entity\Complement;
use App\Repository\BurgerRepository;
use App\Repository\ComplementRepository;
use Cloudinary\Cloudinary;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\RedirectResponse;

// src/Controller/Admin/ComplementController.php
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
}
