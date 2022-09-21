using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 备案清单【设备列表】
    /// </summary>
    public class t_equipment : tablebase
    {
        [Key]
        public int id { get; set; }

        public int beianId { get; set; }

        public int companyId { get; set; }

        /// <summary>
        /// 设备名称
        /// </summary>
        [Required, MaxLength(200)]
        public string ename { get; set; }

        /// <summary>
        /// 规格型号
        /// </summary>
        [Required, MaxLength(100)]
        public String emodal { get; set; }

        /// <summary>
        /// 单位
        /// </summary>
        [Required, MaxLength(10)]
        public string unit { get; set; }

        /// <summary>
        /// 数量
        /// </summary>
        // [Column(TypeName = "decimal(18,2)")]
        public int quantity { get; set; }
        
        /// <summary>
        /// 单价（元）
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal unitPrice { get; set; }

        /// <summary>
        /// 金额（元）
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal amount { get; set; }

        /// <summary>
        /// 功率(kw)
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal usagePower { get; set; }

        /// <summary>
        /// 负荷系数
        /// </summary>
        [Column(TypeName = "decimal(5,4)")]
        public decimal loadFactor { get; set; }
        
        /// <summary>
        /// 稼动率
        /// </summary>
        [Column(TypeName = "decimal(5,4)")]
        public decimal runRate { get; set; }
        
        ///<summary>
        ///用电(kw·h)
        /// powerUsed = usagePower * loadFactor * runRate * workHour
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? powerUsed { get; set; }
        /// <summary>
        /// 运行时间
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal workHour { get; set; }

        /////////////////////////////////////////////
        /* producePlace已废弃 */
        /// <summary>
        /// 产地
        /// </summary>
        [MaxLength(200)]
        public String producePlace { get; set; }
    }
}
