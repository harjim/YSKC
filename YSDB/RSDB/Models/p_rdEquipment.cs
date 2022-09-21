using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 设备研发折旧表
    /// </summary>
    public class p_rdEquipment : tablebase
    {
        [Key]
        public int id { get; set; }

        public int companyId { get; set; }
        /// <summary>
        /// 项目Id
        /// </summary>
        public int projectId { get; set; }

        /// <summary>
        /// 使用月份
        /// </summary>
        public DateTime month { get; set; }

        /// <summary>
        /// 研发折旧
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal rdDepreciation { get; set; }
        /// <summary>
        /// 设备编号
        /// </summary>
        [Required, MaxLength(100)]
        public string ecode { get; set; }
        /// <summary>
        /// 研发工时
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal rdHour { get; set; }

        [Column(TypeName = "decimal(18,2)")]
        public decimal rdRatio { get; set; }

        /// <summary>
        /// 电费费用
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? powerRate { get; set; }

        /// <summary>
        /// 电费单价
        /// </summary>
        [Column(TypeName = "decimal(18,6)")]
        public decimal? powerUnitPrice { get; set; }
    }
}
