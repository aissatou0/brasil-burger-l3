using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace BrasilBurger.Web.Models.Entities;

[Table("menus")]
public class Menu
{
    [Key]
    [Column("id")]
    public int Id { get; set; }

    [Column("nom")]
    public string Nom { get; set; } = string.Empty;

    [Column("image")]
    public string? Image { get; set; }

    [Column("actif")]
    public bool Actif { get; set; }
}
