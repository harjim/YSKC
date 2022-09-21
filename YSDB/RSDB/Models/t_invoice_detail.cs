using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 发票明细【发票子表】
    /// </summary>
    public class t_invoice_detail : tablebase
    {
        [Key]
        public int id { get; set; }

        public int companyId { get; set; }

        ///<summary>
        /// 投资id
        ///</summary>
        public int? investmentId { get; set; }

        public int invoiceId { get; set; }

        [Required, MaxLength(200)]
        public string ename { get; set; }

        [Required, MaxLength(100)]
        public string emodal { get; set; }

        ///<summary>
        /// 数量
        ///</summary>
        public int quantity { get; set; }

        ///<summary>
        /// 不含税单价
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal excludeTaxPrice { get; set; }

        ///<summary>
        /// 税率
        ///</summary>
        [Column(TypeName = "decimal(4,2)")]
        public decimal taxRate { get; set; }

        ///<summary>
        /// 含税单价
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal taxPrice { get; set; }

        ///<summary>
        /// 不含税总额
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal excludeTaxAmount { get; set; }

        ///<summary>
        /// 含税总额
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal taxAmount { get; set; }
    }
}
