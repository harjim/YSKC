using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 项目人员考勤
    /// </summary>
    public class p_attendance_month : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 公司id
        /// </summary>
        public int companyId { get; set; }
        /// <summary>
        /// 项目Id
        /// </summary>
        public int projectId { get; set; }

        /// <summary>
        /// 月份
        /// </summary>
        public DateTime month { get; set; }
        /// <summary>
        /// 当前考勤数据(小时)
        /// </summary>
        [Required, MaxLength(500)]
        public string hoursAttData { get; set; }
        /// <summary>
        /// 当前考勤数据(人数)
        /// </summary>
        [Required, MaxLength(500)]
        public string personAttData { get; set; }
    }
}
