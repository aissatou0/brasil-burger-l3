using BrasilBurger.Web.Services;
using Microsoft.AspNetCore.Mvc;

namespace BrasilBurger.Web.Controllers;

public class CartController : Controller
{
    private readonly CartService _cartService;

    public CartController(CartService cartService)
    {
        _cartService = cartService;
    }

    [HttpGet("/Cart")]
    public IActionResult Index()
    {
        if (HttpContext.Session.GetInt32("ClientId") == null)
            return RedirectToAction("Login", "Auth");

        var cart = _cartService.GetCart(HttpContext.Session);
        ViewBag.CartCount = _cartService.GetCount(HttpContext.Session);
        return View(cart);
    }

    [HttpGet("/Cart/Count")]
    public IActionResult Count()
    {
        var count = _cartService.GetCount(HttpContext.Session);
        return Json(new { count });
    }

    public record AddToCartRequest(string Type, int ItemId, string Nom, decimal Prix, string? Image, int Quantite);

    [HttpPost("/Cart/Add")]
    public IActionResult Add([FromBody] AddToCartRequest req)
    {
        if (HttpContext.Session.GetInt32("ClientId") == null)
            return Unauthorized();

        var count = _cartService.Add(
            HttpContext.Session,
            new CartService.CartItem(req.Type, req.ItemId, req.Nom, req.Prix, req.Image, req.Quantite),
            req.Quantite <= 0 ? 1 : req.Quantite
        );

        return Json(new { count });
    }

    [HttpPost("/Cart/Clear")]
    public IActionResult Clear()
    {
        _cartService.Clear(HttpContext.Session);
        return RedirectToAction("Index");
    }
}
