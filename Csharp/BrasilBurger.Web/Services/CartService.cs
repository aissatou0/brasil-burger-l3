using System.Text.Json;

namespace BrasilBurger.Web.Services;

public class CartService
{
    private const string SessionKey = "CART";

    public record CartItem(string Type, int ItemId, string Nom, decimal Prix, string? Image, int Quantite);

    public List<CartItem> GetCart(ISession session)
    {
        var json = session.GetString(SessionKey);
        return string.IsNullOrWhiteSpace(json)
            ? new List<CartItem>()
            : JsonSerializer.Deserialize<List<CartItem>>(json) ?? new List<CartItem>();
    }

    public void SaveCart(ISession session, List<CartItem> cart)
    {
        session.SetString(SessionKey, JsonSerializer.Serialize(cart));
    }

    public int GetCount(ISession session) => GetCart(session).Sum(x => x.Quantite);

    public int Add(ISession session, CartItem item, int qty = 1)
    {
        var cart = GetCart(session);

        var existing = cart.FirstOrDefault(x => x.Type == item.Type && x.ItemId == item.ItemId);
        if (existing is null)
        {
            cart.Add(item with { Quantite = qty });
        }
        else
        {
            cart.Remove(existing);
            cart.Add(existing with { Quantite = existing.Quantite + qty });
        }

        SaveCart(session, cart);
        return cart.Sum(x => x.Quantite);
    }

    public void Clear(ISession session) => session.Remove(SessionKey);
}
