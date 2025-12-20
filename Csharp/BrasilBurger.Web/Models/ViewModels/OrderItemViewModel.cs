namespace BrasilBurger.Web.Models.ViewModels;

public class OrderItemViewModel
{
    public string Nom { get; set; } = string.Empty;
    public string Image { get; set; } = string.Empty;
    public int Quantite { get; set; }
    public decimal Prix { get; set; }
}
