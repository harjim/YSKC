using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 人员研发计划表
    /// </summary>
    public class p_rdEmployee_plan : tablebase
    {
        [Key]
        public int id { get; set; }

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
        /// 员工编号/标识
        /// </summary>
        [Required, MaxLength(30)]
        public string enumber { get; set; }
        /// <summary>
        /// 计划工时
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal planTime { get; set; }
    }
}
