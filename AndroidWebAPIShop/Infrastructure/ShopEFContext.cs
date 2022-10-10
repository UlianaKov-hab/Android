using ApplicationCore.Entities;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Infrastructure
{
    public class ShopEFContext : DbContext
    {
        public ShopEFContext(DbContextOptions<ShopEFContext> options) : base(options)
        {
        }

        public DbSet<CategoryEntity> Categories { get; set; }


    }
}
