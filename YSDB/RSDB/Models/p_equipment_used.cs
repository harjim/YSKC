using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class p_equipment_used : tablebase
    {
        [Key]
        public int id { get; set; }

        public int companyId { get; set; }
        /// <summary>
        /// 设备编码
        /// </summary>
        [Required, MaxLength(100)]
        public string ecode { get; set; }
        /// <summary>
        /// 使用月份
        /// </summary>
        public DateTime month { get; set; }
        /// <summary>
        /// 已经使用考勤数据(小时)
        /// </summary>
        [Required, MaxLength(200)]
        public string usedEquData { get; set; }
        /// <summary>
        /// 总工时
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal workHours { get; set; }
        /// <summary>
        /// 剩余工时
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal remainHours { get; set; }
    }
}
