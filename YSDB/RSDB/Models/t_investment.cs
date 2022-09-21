using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 投资清单
    /// </summary>
    public class t_investment : tablebase
    {
        [Key]
        public int id { get; set; }

        public int beianId { get; set; }

        public int companyId { get; set; }

        /// <summary>
        /// 支出类别
        /// </summary>
        [Required, MaxLength(200)]
        public String type { get; set; }

        /// <summary>
        /// 设备名称
        /// </summary>
        [Required, MaxLength(200)]
        public string ename { get; set; }

        /// <summary>
        /// 设备型号
        /// </summary>
        [Required, MaxLength(100)]
        public String emodal { get; set; }

        /// <summary>
        /// 入账时间
        /// </summary>
        public DateTime? entryDate { get; set; }

        /// <summary>
        /// 排序，默认为0 (资产序号)
        /// </summary>
        public int order { get; set; }

        /// <summary>
        /// 功率
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
        /// <summary>
        /// 运行时间
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal workHour { get; set; }
    }
}
