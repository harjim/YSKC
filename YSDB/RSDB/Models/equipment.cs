using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 设备表
    /// </summary>
    public class equipment : tablebase
    {
        [Key]
        public int id { get; set; }
        public int companyId { get; set; }
        /// <summary>
        /// 设备名称
        /// </summary>
        [Required, MaxLength(200)]
        public string ename { get; set; }
        /// <summary>
        /// 资产编码
        /// </summary>
        [Required, MaxLength(100)]
        public string ecode { get; set; }
        /// <summary>
        /// 设备型号
        /// </summary>
        [Required, MaxLength(100)]
        public String emodal { get; set; }
        /// <summary>
        /// 单价
        /// </summary>
        public decimal unitPrice { get; set; }
        /// <summary>
        /// 单位
        /// </summary>
        [Required, MaxLength(10)]
        public string unit { get; set; }

        /// <summary>
        /// 数量
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal quantity { get; set; }
        /// <summary>
        /// 使用年限
        /// </summary>
        public int usefullife { get; set; }
        /// <summary>
        /// 采购日期
        /// </summary>
        public DateTime? purchaseDate { get; set; }

        /// <summary>
        /// 折旧日期（即开始使用日期）
        /// </summary>
        public DateTime? depreciationDate { get; set; }

        /// <summary>
        /// 月折旧率,保留4位小数
        /// </summary>
        public decimal depreciationRate { get; set; }
        /// <summary>
        /// 功率
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal usagePower { get; set; }

        [MaxLength(300)]
        public String remark { get; set; }
        /// <summary>
        /// 部门id
        /// </summary>
        public int? deptId { get; set; }

        /// <summary>
        /// 设备类型【30100：仪器，30000：设备，40001：软件摊销】
        /// </summary>
        public int etype { get; set; }

        [MaxLength(100)]
        public string kinds { get; set; }

        /// <summary>
        /// 工艺线id
        /// </summary>
        public int? workshopId { get; set; }

        /// <summary>
        /// 部门
        /// </summary>
        [MaxLength(100)]
        public string deptName { get; set; }
        /// <summary>
        /// 车间
        /// </summary>
        [MaxLength(100)]
        public string workshop { get; set; }
        /// <summary>
        /// 工艺线
        /// </summary>
        [MaxLength(100)]
        public string productLine { get; set; }
        /// <summary>
        /// 工艺段
        /// </summary>
        [MaxLength(100)]
        public string processSection { get; set; }

        [MaxLength(2000)]
        public string data { get; set; }
        ///<summary>
        /// 报废/停用
        ///</summary>
        public DateTime? invalidated { get; set; }

    }
}
