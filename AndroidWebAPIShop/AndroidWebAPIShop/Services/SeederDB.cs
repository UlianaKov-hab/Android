﻿using AndroidWebAPIShop.Constants;
using ApplicationCore.Entities;
using ApplicationCore.Entities.Identity;
using Infrastructure;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;

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
                
                context.Database.Migrate();  ///автоматично накатує міграції якщо їх немає.

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
                if(!context.Categories.Any())
                {
                    var category = new CategoryEntity
                    {
                        Name = "Комп'ютери та ноутбуки",
                        DateCreated = DateTime.SpecifyKind(DateTime.Now, DateTimeKind.Utc),
                        Image = "laptop.jpeg",
                        Priority = 1,
                        Description = "Ноутбуки і ПК для кожного"
                    };
                    context.Categories.Add(category);
                    context.SaveChanges();
                }
               

            }
        }
    }
}
