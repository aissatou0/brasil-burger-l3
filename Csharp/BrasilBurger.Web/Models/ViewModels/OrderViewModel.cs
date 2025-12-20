namespace BrasilBurger.Web.Models.ViewModels;

public class OrderViewModel
{
    public int Id { get; set; }
    public DateTime DateCommande { get; set; }
    public string EtatCommande { get; set; } = "EN_COURS";
    public decimal Total { get; set; }

    public List<OrderItemViewModel> Items { get; set; } = new();
}
