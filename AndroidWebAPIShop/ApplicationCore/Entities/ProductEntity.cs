using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ApplicationCore.Entities
{
    public class ProductEntity : BaseEntity<int>
    {
        [Required, StringLength(255)]
        public string Name { get; set; }
        [Required]
        public double Price { get; set; }
        [Required, StringLength(4000)]
        public string Description { get; set; }
        public ProductEntity()
        {

        }
    }
}
