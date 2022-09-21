using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 员工考勤
    /// </summary>
    public class d_attendance: tablebase
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
        /// 考勤月份
        /// </summary>
        public DateTime month { get; set; }
        /// <summary>
        /// 考勤数据(小时)
        /// </summary>
        [Required,MaxLength(120)]
        public string attData { get; set; }

        /// <summary>
        /// 剩余的考勤数据(小时)
        /// </summary>
        [Required, MaxLength(120)]
        
        public string remainAttData { get; set; }


        public int companyId { get; set; }

        [MaxLength(200)]
        public string remark { get; set; }

    }
}
