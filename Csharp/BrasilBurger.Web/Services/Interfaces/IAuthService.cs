using BrasilBurger.Web.Models;

namespace BrasilBurger.Web.Services.Interfaces;

public interface IAuthService
{
    Client? Authenticate(string email, string password);
}
