using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 奖金表
    /// </summary>
    public class d_bonus : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 员工编号/标识
        /// </summary>
        [Required, MaxLength(30)]
        public string enumber { get; set; }
        /// <summary>
        /// 姓名
        /// </summary>
        [Required, MaxLength(20)]
        public string ename { get; set; }
        /// <summary>
        /// 月份
        /// </summary>
        public DateTime month { get; set; }
        /// <summary>
        /// 奖金开始日
        /// </summary>
        public int beginDay { get; set; }
        /// <summary>
        /// 奖金结束日
        /// </summary>
        public int endDay { get; set; }
        /// <summary>
        /// 公司id
        /// </summary>
        public int companyId { get; set; }

        [MaxLength(200)]
        public string remark { get; set; }
        /// <summary>
        /// 奖金
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal bonus { get; set; }
        /// <summary>
        /// 拆分之前的总金额
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal totalBonus { get; set; }
        /// <summary>
        /// 奖金来源开始时间
        /// </summary>
        public DateTime startTime { get; set; }
        /// <summary>
        /// 奖金来源结束时间
        /// </summary>
        public DateTime endTime { get; set; }
        /// <summary>
        /// 是否已经使用
        /// </summary>
        public bool isUsed { get; set; }
        /// <summary>
        /// 关联科目id
        /// </summary>
        public int? accountTitleId { get; set; }

        /// <summary>
        /// 部门
        /// </summary>
        [MaxLength(100)]
        public string deptName { get; set; }
    }
}
