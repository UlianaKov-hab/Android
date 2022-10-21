using AndroidWebAPIShop.Constants;
using ApplicationCore.Entities;
using ApplicationCore.Entities.Identity;
using Infrastructure;
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
                var context = scope.ServiceProvider
                    .GetRequiredService<ShopEFContext>();

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
                if (!userManager.Users.Any())
                {
                    AppUser user = new AppUser
                    {
                        FirstName = "Петро",
                        SecondName = "Мельник",
                        Photo = "user.jpg",
                        Email = "user@gmail.com",
                        UserName = "user@gmail.com"
                    };
                    var result = userManager.CreateAsync(user, "Qwerty1-").Result;
                    result = userManager.AddToRoleAsync(user, Roles.User).Result;

                    AppUser admin = new AppUser
                    {
                        FirstName = "Олег",
                        SecondName = "Коваль",
                        Photo = "admin.jpg",
                        Email = "admin@gmail.com",
                        UserName = "admin@gmail.com"
                    };
                    result = userManager.CreateAsync(admin, "Qwerty1-+").Result;
                    result = userManager.AddToRoleAsync(admin, Roles.Admin).Result;
                }
                if (!context.Posts.Any())
                {
                    var user = userManager.FindByEmailAsync("admin@gmail.com").Result;
                    if(user != null)
                    {
                        var post = new PostEntity
                        {
                            //дата адаптується під регіон
                            DateCreated = DateTime.SpecifyKind(DateTime.Now, DateTimeKind.Utc),
                            Title = "Гей парад розігнали",
                            UserCreateId = user.Id,
                            Description = "Під охороною. Реакція суспільства не однозначна"
                        };
                        context.Posts.Add(post);
                        context.SaveChanges();
                        var postSelect = new UserPostSelect
                        {
                            UserId = user.Id,
                            PostId = post.Id,
                        };
                        context.UserPostSelects.Add(postSelect);
                        context.SaveChanges();
                    }
                }


            }
        }
    }
}
