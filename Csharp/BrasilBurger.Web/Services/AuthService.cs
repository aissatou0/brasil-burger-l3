using BrasilBurger.Web.Models;
using BrasilBurger.Web.Repositories.Interfaces;
using BrasilBurger.Web.Services.Interfaces;
using BCrypt.Net;

namespace BrasilBurger.Web.Services;

public class AuthService : IAuthService
{
    private readonly IClientRepository _clientRepository;

    public AuthService(IClientRepository clientRepository)
    {
        _clientRepository = clientRepository;
    }

    public Client? Authenticate(string email, string password)
    {
        var client = _clientRepository.GetByEmail(email);

        if (client == null)
            return null;

        //bool passwordOk = BCrypt.Net.BCrypt.Verify(password, client.Password);
    bool passwordOk = client.Password == password;

        return passwordOk ? client : null;
    }
}
