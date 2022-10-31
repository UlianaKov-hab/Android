using AndroidWebAPIShop.Constants;
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
                if (!context.Products.Any())
                {                    
                    CategoryEntity category = context.Categories.FirstOrDefault(c => c.Name == "Комп'ютери та ноутбуки");
                    if(category != null)
                    {
                        ProductImage image = new ProductImage { Name = "laptop.jpeg" };
                        ProductImage image2 = new ProductImage { Name = "laptop2.jpeg" };
                        ProductImage image3 = new ProductImage { Name = "laptop3.jpeg" };

                        List<ProductImage> images = new List<ProductImage> { image, image2, image3 };
                        context.ProductImage.AddRange(images);
                        context.SaveChanges();


                        var product = new ProductEntity
                        {
                            Name = "Ноутбук Apple MacBook",
                            Price = 42999,
                            Description = "Екран 13.3'' Retina(2560x1600) WQXGA, глянсовий / Apple M1 / RAM 8 ГБ / SSD 256 ГБ / Apple M1 Graphics / Wi - Fi / Bluetooth / macOS Big Sur / 1.29 кг / сірий",
                            Category = category

                        };
                        context.Products.Add(product);
                        context.SaveChanges();

                        ProductImageProduct a = new ProductImageProduct
                        {
                            ProductId = product.Id,
                            ProductImageId = image.Id
                        };
                        ProductImageProduct b = new ProductImageProduct
                        {
                            ProductId = product.Id,
                            ProductImageId = image2.Id
                        };
                        ProductImageProduct c = new ProductImageProduct
                        {
                            ProductId = product.Id,
                            ProductImageId = image3.Id
                        };
                        context.ImageProducts.Add(a);
                        context.ImageProducts.Add(b);
                        context.ImageProducts.Add(c);
                       
                        context.SaveChanges();
                    }
                }
            }
        }
    }
}
