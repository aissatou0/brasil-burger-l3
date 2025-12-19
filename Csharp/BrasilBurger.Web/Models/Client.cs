using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace BrasilBurger.Web.Models;

[Table("clients")]
public class Client
{
    [Key]
    [Column("id")]
    public int Id { get; set; }


    [Required]
    [Column("nom")]
    public string Nom { get; set; } = string.Empty;

    [Required]
    [Column("prenom")]
    public string Prenom { get; set; } = string.Empty;

    [Column("email")]
    [Required]
    public string Email { get; set; } = string.Empty;

    [Column("telephone")]
    [Required]
    public string? Telephone { get; set; }

    [Column("password")]
    [Required]
    public string Password { get; set; } = string.Empty;
}
