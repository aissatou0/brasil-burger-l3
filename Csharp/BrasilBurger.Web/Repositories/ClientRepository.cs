using BrasilBurger.Web.Data;
using BrasilBurger.Web.Models;
using BrasilBurger.Web.Repositories.Interfaces;

namespace BrasilBurger.Web.Repositories;

public class ClientRepository : IClientRepository
{
    private readonly ApplicationDbContext _context;

    public ClientRepository(ApplicationDbContext context)
    {
        _context = context;
    }

    public Client? GetByEmail(string email)
    {
        return _context.Clients.FirstOrDefault(c => c.Email == email);
    }
}
