using BrasilBurger.Web.Models;

namespace BrasilBurger.Web.Repositories.Interfaces;

public interface IClientRepository
{
    Client? GetByEmail(string email);
}
