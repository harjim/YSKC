using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 项目预算表
    /// </summary>
    public class p_budget : tablebase
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
        /// 字典表的类型
        /// </summary>
        public byte type { get; set; }
        /// <summary>
        /// 字典key
        /// </summary>
        [MaxLength(50)]
        public string key { get; set; }
        /// <summary>
        /// 预算
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal value { get; set; }
        /// <summary>
        /// 月份(置空，不处理)
        /// </summary>
        public DateTime? month { get; set; }
        /// <summary>
        /// 所属阶段
        /// </summary>
        [MaxLength(10)]
        public string stage { get; set; }

        public int year { get; set; }
    }
}
