using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class t_projectCost : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 项目id，t_project
        /// </summary>
        public int projectId { get; set; }
        /// <summary>
        /// 公司id
        /// </summary>
        public int companyId { get; set; }

        public int ctype { get; set; }
        [Required, MaxLength(200)]
        public string cname { get; set; }
        [Required, MaxLength(30)]
        public string model { get; set; }
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal fillAmount { get; set; }
        [Required, MaxLength(200)]
        public string payDates { get; set; }
        [Required, MaxLength(50)]
        public string payee { get; set; }
        [Required, MaxLength(200)]
        public string invoiceVoucher { get; set; }
        [Required, MaxLength(200)]
        public string invoiceNumber { get; set; }

        public DateTime invoiceDate { get; set; }

        public int isBankTransfer { get; set; }
        [Required, MaxLength(200)]
        public string bankVoucher { get; set; }
        [Required, MaxLength(200)]
        public string bankTransferDates { get; set; }
        [Required, MaxLength(40)]
        public string contractNumber { get; set; }

        public DateTime contractDate { get; set; }
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal auditAmount { get; set; }

        public int quantity { get; set; }
    }
}
