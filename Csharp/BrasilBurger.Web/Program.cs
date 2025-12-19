using BrasilBurger.Web.Data;
using Microsoft.EntityFrameworkCore;
using BrasilBurger.Web.Repositories;
using BrasilBurger.Web.Repositories.Interfaces;
using BrasilBurger.Web.Services;
using BrasilBurger.Web.Services.Interfaces;
var builder = WebApplication.CreateBuilder(args);

builder.Services.AddControllersWithViews();

builder.Services.AddDbContext<ApplicationDbContext>(options =>
    options.UseNpgsql(builder.Configuration.GetConnectionString("DefaultConnection")));

builder.Services.AddScoped<IClientRepository, ClientRepository>();
builder.Services.AddScoped<IAuthService, AuthService>();

builder.Services.AddSession();




var app = builder.Build();

app.UseStaticFiles();
app.UseRouting();
app.UseSession();
app.UseAuthorization();

app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Home}/{action=Index}/{id?}");

app.Run();
