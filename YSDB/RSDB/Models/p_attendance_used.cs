using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 考勤使用表
    /// </summary>
    public class p_attendance_used : tablebase
    {
        [Key]
        public int id { get; set; }

        public int companyId { get; set; }
        /// <summary>
        /// 员工编号/标识
        /// </summary>
        [Required, MaxLength(30)]
        public string enumber { get; set; }
        /// <summary>
        /// 工时
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? workHour { get; set; }
        /// <summary>
        /// 使用日期
        /// </summary>
        public DateTime workDate { get; set; }

        /// <summary>
        /// 剩余工时
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? remainHours { get; set; }

        /// <summary>
        /// 时间区间(,)号分割
        /// </summary>
        [MaxLength(200)]
        public string timeRange { get; set; }
    }
}
