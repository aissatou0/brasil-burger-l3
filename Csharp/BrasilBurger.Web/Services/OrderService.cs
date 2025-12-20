using BrasilBurger.Web.Data;
using BrasilBurger.Web.Models.ViewModels;
using Microsoft.EntityFrameworkCore;

namespace BrasilBurger.Web.Services;

public class OrderService
{
    private readonly ApplicationDbContext _context;

    public OrderService(ApplicationDbContext context)
    {
        _context = context;
    }

    public List<OrderViewModel> GetOrdersByClient(int clientId)
    {
        return _context.Commandes
            .Where(c => c.IdClient == clientId)
            .OrderByDescending(c => c.DateCommande)
            .Select(c => new OrderViewModel
            {
                Id = c.Id,
                DateCommande = c.DateCommande,
                EtatCommande = c.EtatCommande,
                Total = c.Total,
                Items = _context.CommandeItems
                    .Where(i => i.IdCommande == c.Id)
                    .Select(i => new OrderItemViewModel
                    {
                        Nom = i.TypeItem + " #" + i.IdItem, // 🔧 temporaire
                        Quantite = i.Quantite,
                        Prix = i.Prix,
                        Image = "" // image ajoutée plus tard
                    })
                    .ToList()
            })
            .ToList();
    }
}
