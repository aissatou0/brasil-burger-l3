using Microsoft.AspNetCore.Mvc;
using BrasilBurger.Web.Services;
using BrasilBurger.Web.Models.ViewModels;

namespace BrasilBurger.Web.Controllers;

public class CartController : Controller
{
    private readonly CartService _cartService;

    public CartController(CartService cartService)
    {
        _cartService = cartService;
    }

    public IActionResult Index()
    {
        if (HttpContext.Session.GetInt32("ClientId") == null)
            return RedirectToAction("Login", "Auth");

        var cart = _cartService.GetCart(HttpContext);
        ViewBag.Total = _cartService.Total(HttpContext);
        return View(cart);
    }

    [HttpPost]
    public IActionResult Add([FromBody] CartItem item)
    {
        _cartService.AddItem(HttpContext, item);

        return Json(new
        {
            count = _cartService.Count(HttpContext)
        });
    }

    [HttpGet]
    public IActionResult Count()
    {
        return Json(new
        {
            count = _cartService.Count(HttpContext)
        });
    }

    [HttpPost]
    public IActionResult Remove(string type, int id)
    {
        _cartService.RemoveItem(HttpContext, type, id);
        return RedirectToAction("Index");
    }
}
