namespace BrasilBurger.Web.Models.ViewModels;

public class CartItem
{
    public string Type { get; set; } = string.Empty;   // burger | menu | complement
    public int ItemId { get; set; }
    public string Nom { get; set; } = string.Empty;
    public decimal Prix { get; set; }
    public string Image { get; set; } = string.Empty;
    public int Quantite { get; set; } = 1;

    public decimal Total => Prix * Quantite;
}
