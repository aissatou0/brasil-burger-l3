using Microsoft.EntityFrameworkCore;
using BrasilBurger.Web.Models;

namespace BrasilBurger.Web.Data;

public class ApplicationDbContext : DbContext
{
    public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
        : base(options) { }

    public DbSet<Client> Clients => Set<Client>();
}
