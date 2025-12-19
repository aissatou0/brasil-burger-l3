using BrasilBurger.Web.Data;
using Microsoft.EntityFrameworkCore;
using BrasilBurger.Web.Repositories;
using BrasilBurger.Web.Repositories.Interfaces;
using BrasilBurger.Web.Services;
using BrasilBurger.Web.Services.Interfaces;

var builder = WebApplication.CreateBuilder(args);

// MVC
builder.Services.AddControllersWithViews();

// DB CONTEXT (NEON)
builder.Services.AddDbContext<ApplicationDbContext>(options =>
    options.UseNpgsql(builder.Configuration.GetConnectionString("DefaultConnection")));

// DEPENDENCY INJECTION
builder.Services.AddScoped<IClientRepository, ClientRepository>();
builder.Services.AddScoped<IAuthService, AuthService>();
builder.Services.AddScoped<CatalogueService>();

// SESSION
builder.Services.AddDistributedMemoryCache();

builder.Services.AddSession(options =>
{
    options.IdleTimeout = TimeSpan.FromHours(2);
    options.Cookie.HttpOnly = true;
    options.Cookie.IsEssential = true;
});

var app = builder.Build();

// MIDDLEWARE PIPELINE
app.UseStaticFiles();
app.UseRouting();

app.UseSession();        // ⚠️ AVANT Authorization
app.UseAuthorization();

// ROUTES
app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Home}/{action=Index}/{id?}");

app.Run();
