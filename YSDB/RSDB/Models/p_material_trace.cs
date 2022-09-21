using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 物料追踪表
    /// </summary>
    public class p_material_trace : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 项目使用物料ID
        /// </summary>
        public int pId { get; set; }
        /// <summary>
        /// 正常产出率
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal normalOutputRate { get; set; }
        /// <summary>
        /// 研发产出率
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal rdOutputRate { get; set; }
        /// <summary>
        /// 研发产出量
        /// </summary>
        /// 
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal rdOutPut { get; set; }
        /// <summary>
        /// 研发产出金额
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal rdOutputAmount { get; set; }
        /// <summary>
        /// 研发损耗率
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal rdLossRate { get; set; }
        /// <summary>
        /// 研发损耗量
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal rdLoss { get; set; }
        /// <summary>
        /// 研发损耗金额
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal rdLossAmount { get; set; }

        /// <summary>
        /// 报废率
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal scrapRate { get; set; }
        /// <summary>
        /// 报废量
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal scrap { get; set; }
        /// <summary>
        /// 报废金额
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal scrapAmount { get; set; }
        /// <summary>
        /// 报废单号
        /// </summary>
        [MaxLength(100)]
        public string scrapNo { get; set; }
        /// <summary>
        /// 样品收入
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal sampleRevenue { get; set; }
        /// <summary>
        /// 废料售价
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal scrapPrice { get; set; }
        /// <summary>
        /// 特殊收入
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal specialIncome { get; set; }
        /// <summary>
        /// 特殊收入单号
        /// </summary>
        [MaxLength(100)]
        public string specialIncomeNo { get; set; }
    }
}
