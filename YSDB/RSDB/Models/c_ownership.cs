using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 公司股权架构
    /// </summary>
    public class c_ownership : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 公司id
        /// </summary>
        public int companyId { get; set; }
        /// <summary>
        /// 股东名称
        /// </summary>
        [Required, MaxLength(50)]
        public string shareholder { get; set; }
        /// <summary>
        /// 出资额（万元）
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal capitalContribution { get; set; }
        /// <summary>
        /// 出资方式
        /// </summary>
        [Required, MaxLength(100)]
        public string contributionType { get; set; }
        /// <summary>
        /// 比例
        /// </summary>
        [Required,Column(TypeName = "decimal(5,2)")]
        public decimal proportion { get; set; }
    }
}
