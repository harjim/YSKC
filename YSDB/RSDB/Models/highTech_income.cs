using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 高新产品收入
    /// </summary>
    public class highTech_income : tablebase
    {
        [Key]
        public int id { get; set; } 
        public int companyId { get; set; }
        /// <summary>
        /// 高新技术id
        /// </summary>
        public int? highTechId { get; set; }
        /// <summary>
        /// 产品名称
        /// </summary>
        [MaxLength(200),Required]
        public string productName { get; set; }
        /// <summary>
        /// 记账日期
        /// </summary>
        public DateTime bookDate { get; set; }
        /// <summary>
        /// 凭证号
        /// </summary>
        [MaxLength(50),Required]
        public string voucherNo { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal quantity { get; set; }
        /// <summary>
        /// 单价
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal unitPrice { get; set; }
        /// <summary>
        /// 高新收入
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal income { get; set; }
        /// <summary>
        /// 客户名称
        /// </summary>
        [Required,MaxLength(100)]
        public string client { get; set; }
    }
}
