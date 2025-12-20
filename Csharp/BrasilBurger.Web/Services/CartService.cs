using System.Text.Json;
using BrasilBurger.Web.Models.ViewModels;

namespace BrasilBurger.Web.Services;

public class CartService
{
    private const string CART_KEY = "CART";

    public List<CartItem> GetCart(HttpContext context)
    {
        var json = context.Session.GetString(CART_KEY);
        return json == null
            ? new List<CartItem>()
            : JsonSerializer.Deserialize<List<CartItem>>(json)!;
    }

    public void SaveCart(HttpContext context, List<CartItem> cart)
    {
        context.Session.SetString(CART_KEY, JsonSerializer.Serialize(cart));
    }

    public void AddItem(HttpContext context, CartItem item)
    {
        var cart = GetCart(context);

        var existing = cart.FirstOrDefault(c =>
            c.Type == item.Type && c.ItemId == item.ItemId);

        if (existing != null)
            existing.Quantite++;
        else
            cart.Add(item);

        SaveCart(context, cart);
    }

    public void RemoveItem(HttpContext context, string type, int id)
    {
        var cart = GetCart(context);
        cart.RemoveAll(i => i.Type == type && i.ItemId == id);
        SaveCart(context, cart);
    }

    public int Count(HttpContext context)
        => GetCart(context).Sum(i => i.Quantite);

    public decimal Total(HttpContext context)
        => GetCart(context).Sum(i => i.Total);

    public void Clear(HttpContext context)
        => context.Session.Remove(CART_KEY);
}
