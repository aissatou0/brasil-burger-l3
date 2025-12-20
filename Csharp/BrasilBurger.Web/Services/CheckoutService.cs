using BrasilBurger.Web.Data;
using BrasilBurger.Web.Models.ViewModels;
using BrasilBurger.Web.Models.Entities;

namespace BrasilBurger.Web.Services;

public class CheckoutService
{
    private readonly ApplicationDbContext _context;

    public CheckoutService(ApplicationDbContext context)
    {
        _context = context;
    }

    public int CreateCommande(int clientId, CheckoutViewModel model)
    {
        var commande = new Commande
        {
            IdClient = clientId,
            Total = model.Total,
            TypeCommande = model.TypeCommande ?? "A_EMPORTER",
            DateCommande = DateTime.UtcNow
        };

        _context.Commandes.Add(commande);
        _context.SaveChanges();

        foreach (var item in model.Items)
        {
            _context.CommandeItems.Add(new CommandeItem
            {
                IdCommande = commande.Id,
                TypeItem = NormalizeType(item.Type), // ✅ FIX
                IdItem = item.ItemId,
                Quantite = item.Quantite,
                Prix = item.Prix
            });
        }

        _context.SaveChanges();
        return commande.Id;
    }

    // 🔐 NORMALISATION CENTRALE
    private string NormalizeType(string type)
    {
        return type.ToLower() switch
        {
            "burger" or "burgers" => "BURGER",
            "menu" or "menus" => "MENU",
            "complement" or "complements" => "COMPLEMENT",
            _ => throw new Exception($"TypeItem invalide : {type}")
        };
    }
}
