using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 设备使用记录
    /// </summary>
    public class d_equipment : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 设备编码
        /// </summary>
        [Required, MaxLength(100)]
        public string ecode { get; set; }
        /// <summary>
        /// 设备名称
        /// </summary>
        [Required, MaxLength(200)]
        public string ename { get; set; }
        /// <summary>
        /// 使用月份
        /// </summary>
        public DateTime month { get; set; }

        /// <summary>
        /// 使用数据(小时)
        /// </summary>
        [Required, MaxLength(120)]
        public string equData { get; set; }

        /// <summary>
        /// 剩余的使用数据(小时)
        /// </summary>
        [Required, MaxLength(120)]
        public string remainEquData { get; set; }

        public int companyId { get; set; }

        [MaxLength(200)]
        public string remark { get; set; }

        [Column(TypeName = "decimal(18,2)")]
        public decimal workHours { get; set; }
        /// <summary>
        /// 月折旧
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal depreciation { get; set; }
        /// <summary>
        /// 关联科目id
        /// </summary>
        public int accountTitleId { get; set; }
        /// <summary>
        /// 部门
        /// </summary>
        [MaxLength(100)]
        public string deptName { get; set; }
    }
}
