using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ApplicationCore.Entities
{

    public class ProductImageProduct
    {
        public int ProductId { get; set; }
        public virtual ProductEntity Product { get; set; }
        public int ProductImageId { get; set; }
        public virtual ProductImage ProductImage { get; set; }
    }
}
