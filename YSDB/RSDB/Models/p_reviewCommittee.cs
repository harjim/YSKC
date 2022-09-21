using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 评审委员会名单
    /// </summary>
    public class p_reviewCommittee : tablebase
    {
        [Key]
        public int id { get; set; }

        public int companyId { get; set; }

        /// <summary>
        /// 年份
        /// </summary>
        public int year { get; set; }

        /// <summary>
        /// 员工编号/标识
        /// </summary>
        [Required, MaxLength(30)]
        public string enumber { get; set; }

        public int seq { get; set; }
        
        /// <summary>
        /// 部门
        /// </summary>
        [MaxLength(100)]
        public string deptName { get; set; }
        /// <summary>
        /// 职位
        /// </summary>
        [MaxLength(20)]
        public string position { get; set; }
    }
}
