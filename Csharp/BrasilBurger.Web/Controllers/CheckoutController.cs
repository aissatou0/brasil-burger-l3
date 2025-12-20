using Microsoft.AspNetCore.Mvc;
using BrasilBurger.Web.Services;
using BrasilBurger.Web.Models.ViewModels;

namespace BrasilBurger.Web.Controllers;

public class CheckoutController : Controller
{
    private readonly CartService _cartService;
    private readonly CheckoutService _checkoutService;

    public CheckoutController(CartService cartService, CheckoutService checkoutService)
    {
        _cartService = cartService;
        _checkoutService = checkoutService;
    }

    [HttpGet]
    public IActionResult Index()
    {
        if (HttpContext.Session.GetInt32("ClientId") == null)
            return RedirectToAction("Login", "Auth");

        var items = _cartService.GetCart(HttpContext);

        var model = new CheckoutViewModel
        {
            Items = items,
            Total = _cartService.Total(HttpContext)
        };

        return View(model);
    }

    [HttpPost]
    public IActionResult Confirm(CheckoutViewModel model)
    {
        var clientId = HttpContext.Session.GetInt32("ClientId");
        if (clientId == null)
            return RedirectToAction("Login", "Auth");

        model.Items = _cartService.GetCart(HttpContext);
        model.Total = _cartService.Total(HttpContext);

        var commandeId = _checkoutService.CreateCommande(clientId.Value, model);

        _cartService.Clear(HttpContext);

        return RedirectToAction("Confirmed", new { id = commandeId });
    }

    public IActionResult Confirmed(int id)
    {
        ViewBag.CommandeId = id;
        return View();
    }
}
