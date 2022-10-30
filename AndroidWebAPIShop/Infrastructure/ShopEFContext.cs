using ApplicationCore.Entities;
using ApplicationCore.Entities.Identity;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Infrastructure
{
    public class ShopEFContext : IdentityDbContext<AppUser, AppRole, long, IdentityUserClaim<long>,
        AppUserRole, IdentityUserLogin<long>,
        IdentityRoleClaim<long>, IdentityUserToken<long>>
    {
        public ShopEFContext(DbContextOptions<ShopEFContext> options) : base(options)
        {
        }

        public DbSet<CategoryEntity> Categories { get; set; }
        //ListNews
        public DbSet<PostEntity> Posts { get; set; }

        public DbSet<UserPostSelect> UserPostSelects { get; set; }

        public DbSet<ProductEntity> Products { get; set; }

        public DbSet<ProductImage> ProductImage { get; set; }

        //public DbSet<ProductImageProduct> ImageProducts { get; set; }

        protected override void OnModelCreating(ModelBuilder builder)
        {
            base.OnModelCreating(builder);
            builder.Entity<AppUserRole>(userRole =>
            {
                userRole.HasKey(ur => new { ur.UserId, ur.RoleId });

                userRole.HasOne(ur => ur.Role)
                    .WithMany(r => r.UserRoles)
                    .HasForeignKey(r => r.RoleId)
                    .IsRequired();

                userRole.HasOne(ur => ur.User)
                    .WithMany(r => r.UserRoles)
                    .HasForeignKey(r => r.UserId)
                    .IsRequired();
            });


            //builder.Entity<ProductImageProduct>(productImage =>
            //{
            //    productImage.HasKey(pi => new { pi.ProductId, pi.ProductImageId });

            //    productImage.HasOne(pi => pi.Product)
            //    .WithMany(p => p.ProductImageProducts)
            //    .HasForeignKey(p => p.ProductId);

            //    productImage.HasOne(pi => pi.ProductImage)
            //    .WithMany(p => p.ProductImageProducts)
            //    .HasForeignKey(p => p.ProductImageId);
            //});

            builder.Entity<ProductEntity>(product =>
            {
                product.HasOne(p => p.Category)
                .WithMany(c => c.Products)
                .IsRequired();

            });

            builder.Entity<UserPostSelect>(userPostSelect =>
            {
                userPostSelect.HasKey(ur => new { ur.UserId, ur.PostId });
            });
            //builder.ApplyConfigurationsFromAssembly(Assembly.GetExecutingAssembly());

        }




    }
}
