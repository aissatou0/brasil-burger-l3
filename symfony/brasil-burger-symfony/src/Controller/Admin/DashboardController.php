<?php

namespace App\Controller\Admin;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/admin/dashboard', name: 'admin_dashboard')]
class DashboardController extends AbstractController
{
    public function __invoke(): Response
    {
        return $this->render('admin/dashboard/index.html.twig');
    }
}
