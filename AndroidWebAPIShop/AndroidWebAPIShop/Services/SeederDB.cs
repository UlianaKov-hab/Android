using AndroidWebAPIShop.Constants;
using ApplicationCore.Entities.Identity;
using Microsoft.AspNetCore.Identity;

namespace AndroidWebAPIShop.Services
{
    public static class SeederDB
    {
        public static void SeedData(this IApplicationBuilder app)
        {
            using (var scope = app.ApplicationServices
                .GetRequiredService<IServiceScopeFactory>().CreateScope())
            {
                var userManager = scope.ServiceProvider
                    .GetRequiredService<UserManager<AppUser>>();

                var roleManager = scope.ServiceProvider
                    .GetRequiredService<RoleManager<AppRole>>();

                if(!roleManager.Roles.Any())
                {
                    var result = roleManager.CreateAsync(new AppRole
                    {
                        Name = Roles.User
                    }).Result;
                    result = roleManager.CreateAsync(new AppRole
                    {
                        Name = Roles.Admin
                    }).Result;
                }
            }
        }
    }
}
