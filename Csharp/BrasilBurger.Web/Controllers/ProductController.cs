using BrasilBurger.Web.Data;
using Microsoft.AspNetCore.Mvc;
public class ProductController : Controller
{
    private readonly ApplicationDbContext _context;

    public ProductController(ApplicationDbContext context)
    {
        _context = context;
    }

    public IActionResult Detail(string type, int id)
    {
        object product = type switch
        {
            "burger" => _context.Burgers.Find(id),
            "menu" => _context.Menus.Find(id),
            "complement" => _context.Complements.Find(id),
            _ => null
        };

        if (product == null)
            return NotFound();

        return View(product);
    }
}
