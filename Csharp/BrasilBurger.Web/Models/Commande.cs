namespace BrasilBurger.Web.Models
{
    public class Commande
    {
        public int Id { get; set; }
        public int IdClient { get; set; }
        public decimal Total { get; set; }
        public string EtatCommande { get; set; } = string.Empty;
        public string TypeCommande { get; set; } = string.Empty;
    }
}
