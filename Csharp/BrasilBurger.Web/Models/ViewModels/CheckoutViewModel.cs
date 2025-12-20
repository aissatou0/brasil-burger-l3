using BrasilBurger.Web.Models.ViewModels;

namespace BrasilBurger.Web.Models.ViewModels;

public class CheckoutViewModel
{
    public List<CartItem> Items { get; set; } = new();
    public decimal Total { get; set; }

    // Mode de récupération
    public string TypeCommande { get; set; } = "A_EMPORTER";
    // SUR_PLACE | A_EMPORTER | LIVRAISON

    // Mode de paiement
    public string ModePaiement { get; set; } = "WAVE";
    // WAVE | OM
}
