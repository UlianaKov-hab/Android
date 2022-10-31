using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ApplicationCore.Entities
{
    [Table("tblProducts")]
    public class ProductEntity : BaseEntity<int>
    {
        [Required, StringLength(255)]
        public string Name { get; set; }
        [Required]
        public double Price { get; set; }
        [Required, StringLength(4000)]
        public string Description { get; set; }

        public virtual CategoryEntity Category { get; set; }
        public virtual ICollection<ProductImageProduct> ProductImageProducts { get; set; }


    }
}
