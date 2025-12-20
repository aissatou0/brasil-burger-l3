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
        TypeCommande = model.TypeCommande,
        EtatCommande = "EN_COURS",
        DateCommande = DateTime.UtcNow
    };

    _context.Commandes.Add(commande);
    _context.SaveChanges();

    foreach (var item in model.Items)
    {
        _context.CommandeItems.Add(new CommandeItem
        {
            IdCommande = commande.Id,
            TypeItem = NormalizeType(item.Type),
            IdItem = item.ItemId,
            Quantite = item.Quantite,
            Prix = item.Prix
        });
    }

    // 💳 PAIEMENT
    //_context.Paiements.Add(new Paiement
    //{
    //    IdCommande = commande.Id,
    //    Montant = model.Total,
    //    Mode = model.ModePaiement,
     //   DatePaiement = DateTime.UtcNow
    //});

    _context.SaveChanges();
    return commande.Id;
}


    // 🔐 NORMALISATION CENTRALISÉE ET SAFE
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
