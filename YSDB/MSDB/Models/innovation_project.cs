using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class innovation_project : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 客户id
        /// </summary>
        public int customerId { get; set; }
        /// <summary>
        /// 年份
        /// </summary>
        public int year { get; set; }
        /// <summary>
        /// 部门id
        /// </summary>
        public int deptId { get; set; }
        /// <summary>
        /// 类型：1(创新项目),2（高新项目）
        /// </summary>
        [MaxLength(100)]
        public string types { get; set; }
    }
}
