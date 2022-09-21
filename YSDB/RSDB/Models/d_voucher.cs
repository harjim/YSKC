using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class d_voucher : tablebase
    {

        [Key]
        public int id { get; set; }
        public int companyId { get; set; }
        public DateTime voucherDate { get; set; }

        [Required, MaxLength(50)]
        public string voucherNo { get; set; }
        [Required, MaxLength(200)]
        public string summary { get; set; }
        [Column(TypeName = "decimal(18,2)")]
        public decimal amount { get; set; }
    }
}