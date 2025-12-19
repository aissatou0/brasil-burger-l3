using Microsoft.AspNetCore.Mvc;
using BrasilBurger.Web.Models.ViewModels;
using BrasilBurger.Web.Services;

namespace BrasilBurger.Web.Controllers;

public class HomeController : Controller
{
    private readonly CatalogueService _catalogueService;

    public HomeController(CatalogueService catalogueService)
    {
        _catalogueService = catalogueService;
    }

    public IActionResult Index()
    {
        if (HttpContext.Session.GetInt32("ClientId") == null)
            return RedirectToAction("Login", "Auth");

        ViewBag.ClientNom = HttpContext.Session.GetString("ClientNom");

        var model = _catalogueService.GetCatalogue();

        return View(model);
    }

    [HttpGet]
public IActionResult Filter(string type)
{
    if (HttpContext.Session.GetInt32("ClientId") == null)
        return Unauthorized();

    var catalogue = _catalogueService.GetCatalogue();

    return type switch
    {
        "menus" => Json(catalogue.Menus),
        "burgers" => Json(catalogue.Burgers),
        "complements" => Json(catalogue.Complements),
        _ => Json(new
        {
            menus = catalogue.Menus,
            burgers = catalogue.Burgers,
            complements = catalogue.Complements
        })
    };
}

}
