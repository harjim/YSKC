using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class e_book : tablebase
    {
        [Key]
        public int id { get; set; }
        public int eUserId { get; set; }
        [MaxLength(50), Required]
        public string bookName { get; set; }
        [MaxLength(50), Required]
        public string author { get; set; }
        public int authorOrder { get; set; }
        public DateTime issueDate { get; set; }

        [MaxLength(50), Required]
        public string issueUnit { get; set; }
        [MaxLength(50)]
        public string bookType { get; set; }
    }
}