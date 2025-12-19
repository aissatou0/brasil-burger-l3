using BrasilBurger.Web.Helpers;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Filters;

namespace BrasilBurger.Web.Filters;

public class ClientAuthFilter : IActionFilter
{
    public void OnActionExecuting(ActionExecutingContext context)
    {
        var session = context.HttpContext.Session;
        var clientId = session.GetInt32(SessionClient.ClientId);

        if (clientId == null)
        {
            context.Result = new RedirectToActionResult("Login", "Auth", null);
        }
    }

    public void OnActionExecuted(ActionExecutedContext context) { }
}
