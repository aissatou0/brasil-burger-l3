using Microsoft.AspNetCore.Mvc;
using BrasilBurger.Web.Services.Interfaces;
using BrasilBurger.Web.Models.ViewModels;

namespace BrasilBurger.Web.Controllers;

public class AuthController : Controller
{
    private readonly IAuthService _authService;

    public AuthController(IAuthService authService)
    {
        _authService = authService;
    }

    [HttpGet]
    public IActionResult Login()
    {
        return View();
    }

    [HttpPost]
    public IActionResult Login(LoginViewModel model)
    {
        if (!ModelState.IsValid)
            return View(model);

        var client = _authService.Authenticate(model.Email, model.Password);

        if (client == null)
        {
            ModelState.AddModelError("", "Email ou mot de passe incorrect");
            return View(model);
        }

        // Session
        HttpContext.Session.SetInt32("ClientId", client.Id);
        HttpContext.Session.SetString("ClientNom", client.Nom);

        return RedirectToAction("Index", "Home");
    }

    public IActionResult Logout()
    {
        HttpContext.Session.Clear();
        return RedirectToAction("Login");
    }
}
