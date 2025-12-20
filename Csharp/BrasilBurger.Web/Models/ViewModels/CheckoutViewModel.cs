using BrasilBurger.Web.Models.ViewModels;

namespace BrasilBurger.Web.Models.ViewModels;

public class CheckoutViewModel
{
    public List<CartItem> Items { get; set; } = new();
    public decimal Total { get; set; }

    // SUR_PLACE | A_EMPORTER | LIVRAISON
    public string? TypeCommande { get; set; } = "A_EMPORTER";

    // (optionnel pour la suite)
    public string? ModePaiement { get; set; } // WAVE | OM
}
