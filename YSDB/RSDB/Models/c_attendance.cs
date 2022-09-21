
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 考勤列表
    /// </summary>
    public class c_attendance : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 员工编号/标识
        /// </summary>
        [Required, MaxLength(30)]
        public String enumber { get; set; }
        /// <summary>
        /// 姓名
        /// </summary>
        [Required, MaxLength(20)]
        public String ename { get; set; }
        /// <summary>
        /// 工作日期
        /// </summary>
        public DateTime workDate { get; set; }
        /// <summary>
        /// 工时
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? workHour { get; set; }

        /// <summary>
        /// 上班时间1
        /// </summary>
        public System.TimeSpan onTime1 { get; set; }

        /// <summary>
        /// 下班时间1
        /// </summary>
        public System.TimeSpan? offTime1 { get; set; }

        /// <summary>
        /// 上班时间2
        /// </summary>
        public System.TimeSpan? onTime2 { get; set; }

        /// <summary>
        /// 下班时间2
        /// </summary>
        public System.TimeSpan? offTime2 { get; set; }

        /// <summary>
        /// 上班时间3
        /// </summary>
        public System.TimeSpan? onTime3 { get; set; }

        /// <summary>
        /// 下班时间3
        /// </summary>
        public System.TimeSpan? offTime3 { get; set; }

        public int companyId { get; set; }

        [MaxLength(200)]
        public string remark { get; set; }

        /// <summary>
        /// 部门
        /// </summary>
        [MaxLength(100)]
        public string deptName { get; set; }

    }
}
