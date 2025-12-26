<?php

namespace App\Security;

use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\Security\Http\Authenticator\AbstractLoginFormAuthenticator;
use Symfony\Component\Security\Http\Authenticator\Passport\Passport;
use Symfony\Component\Security\Http\Authenticator\Passport\Badge\UserBadge;
use Symfony\Component\Security\Http\Authenticator\Passport\Credentials\PasswordCredentials;
use Symfony\Component\Security\Core\Authentication\Token\TokenInterface;
use Symfony\Component\HttpFoundation\Response;

class GestionnaireAuthenticator extends AbstractLoginFormAuthenticator
{
    public function authenticate(Request $request): Passport
    {
        return new Passport(
            new UserBadge($request->request->get('email')),
            new PasswordCredentials($request->request->get('password'))
        );
    }

    protected function getLoginUrl(Request $request): string
    {
        return '/admin/login';
    }

    public function onAuthenticationSuccess(
        Request $request,
        TokenInterface $token,
        string $firewallName
    ): ?Response {
        return new RedirectResponse('/admin/dashboard');
    }
}
