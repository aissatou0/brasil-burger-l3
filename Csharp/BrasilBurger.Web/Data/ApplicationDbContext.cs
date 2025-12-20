using Microsoft.EntityFrameworkCore;
using BrasilBurger.Web.Models.Entities;
using BrasilBurger.Web.Models;
namespace BrasilBurger.Web.Data;

public class ApplicationDbContext : DbContext
{
    public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
        : base(options) { }

    public DbSet<Client> Clients => Set<Client>();
    public DbSet<Burger> Burgers => Set<Burger>();
    public DbSet<Menu> Menus => Set<Menu>();
    public DbSet<Complement> Complements => Set<Complement>();
    public DbSet<Commande> Commandes => Set<Commande>();
public DbSet<CommandeItem> CommandeItems => Set<CommandeItem>();

}
