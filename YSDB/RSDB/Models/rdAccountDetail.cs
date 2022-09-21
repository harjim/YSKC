using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 明细帐
    /// </summary>
    public class rdAccountDetail : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 记帐日期
        /// </summary>
        public DateTime rdDate { get; set; }
        /// <summary>
        /// 凭证字号
        /// </summary>
        [Required, MaxLength(20)]
        public string accNumber { get; set; }

        /// <summary>
        /// 摘要
        /// </summary>
        [Required, MaxLength(200)]
        public string summary { get; set; }
        [Column(TypeName = "decimal(18,2)")]
        public decimal debit { get; set; }
        [Column(TypeName = "decimal(18,2)")]
        public decimal credit { get; set; }
        /// <summary>
        /// 记帐方向
        /// </summary>
        [Required, MaxLength(10)]
        public string direction { get; set; }
        [Column(TypeName = "decimal(18,2)")]
        public decimal balance { get; set; }
        /// <summary>
        /// 明细帐类型, 1:研发费用明细帐
        /// </summary>
        public int accType { get; set; }
        public int companyId { get; set; }

    }
}
