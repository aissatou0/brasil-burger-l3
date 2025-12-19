using BrasilBurger.Web.Data;
using BrasilBurger.Web.Models.ViewModels;
using BrasilBurger.Web.Models.Entities;

namespace BrasilBurger.Web.Services;

public class CatalogueService
{
    private readonly ApplicationDbContext _context;

    public CatalogueService(ApplicationDbContext context)
    {
        _context = context;
    }

    public HomeViewModel GetCatalogue()
    {
        return new HomeViewModel
        {
            Burgers = _context.Burgers
                .Where(b => b.Actif)
                .ToList(),

            Menus = _context.Menus
                .Where(m => m.Actif)
                .ToList(),

            Complements = _context.Complements
                .Where(c => c.Actif)
                .ToList()
        };
    }
}
