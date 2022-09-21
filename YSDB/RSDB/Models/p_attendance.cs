using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 人员项目考勤表
    /// </summary>
    public class p_attendance : tablebase
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
        /// 项目Id
        /// </summary>
        public int projectId { get; set; }
        /// <summary>
        /// 考勤数据Id
        /// </summary>
        public int? attendanceDataId { get; set; }
        /// <summary>
        /// 工作日期
        /// </summary>
        public DateTime workDate { get; set; }
        /// <summary>
        /// 工时
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? workHour { get; set; }

        public System.TimeSpan? onTime1 { get; set; }

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

        public bool owner { get; set; }

        ///<summary>
        /// 内容
        ///</summary>
        [MaxLength(300)]
        public string content { get; set; }
    }
}
