using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;
namespace MSDB.Models
{
    public class product_stage : tablebase
    {
        [Key]
        public int id { get; set; }

        public int order { get; set; }

        [Required, MaxLength(10)]
        public string stageKey { get; set; }

        public int productId { get; set; }

    }
}