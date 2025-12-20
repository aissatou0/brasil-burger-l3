using Microsoft.AspNetCore.Mvc;
using BrasilBurger.Web.Services;

namespace BrasilBurger.Web.Controllers;

public class OrdersController : Controller
{
    private readonly OrderService _orderService;

    public OrdersController(OrderService orderService)
    {
        _orderService = orderService;
    }

    public IActionResult Index()
    {
        var clientId = HttpContext.Session.GetInt32("ClientId");
        if (clientId == null)
            return RedirectToAction("Login", "Auth");

        var orders = _orderService.GetOrdersByClient(clientId.Value);
        return View(orders);
    }
}
