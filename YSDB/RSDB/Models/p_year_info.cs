using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 项目年份信息
    /// </summary>
    public class p_year_info : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 项目id
        /// </summary>
        public int projectId { get; set; }
        /// <summary>
        /// 年份
        /// </summary>
        public int year { get; set; }
        /// <summary>
        /// 负责人工号
        /// </summary>
        [MaxLength(50)]
        public string masterENumber { get; set; }
        /// <summary>
        /// 预算
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? budget { get; set; }

        public int companyId { get; set; }
    }
}
