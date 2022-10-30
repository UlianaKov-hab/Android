using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ApplicationCore.Entities
{
    [Table("tblProductImages")]
    public class ProductImage : BaseEntity<int>
    {
        [Required, StringLength(255)]
        public string Name { get; set; }
        //public virtual ICollection<ProductImageProduct> ProductImageProducts { get; set; }

    }
}
