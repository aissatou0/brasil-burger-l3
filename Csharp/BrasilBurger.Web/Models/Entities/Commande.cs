using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace BrasilBurger.Web.Models.Entities;

[Table("commandes")]
public class Commande
{
    [Key]
    [Column("id")]
    public int Id { get; set; }

    [Column("id_client")]
    public int IdClient { get; set; }

    [Column("date_commande")]
    public DateTime DateCommande { get; set; } = DateTime.UtcNow;
    [Column("total")]
    public decimal Total { get; set; }

    [Column("etat_commande")]
    public string EtatCommande { get; set; } = "EN_COURS";

    [Column("type_commande")]
    public string TypeCommande { get; set; } = "A_EMPORTER";
}
