using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 年度报告
    /// </summary>
    public class c_annualReport : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 公司id
        /// </summary>
        public int companyId { get; set; }
        /// <summary>
        /// 年份
        /// </summary>
        public int year { get; set; }
        /// <summary>
        ///  年度报告类型
        /// </summary>
        public int type { get; set; }
        /// <summary>
        /// 报告名称
        /// </summary>
        [Required, MaxLength(100)]
        public string reportName { get; set; }
        /// <summary>
        /// 报告文件路径
        /// </summary>
        [Required, MaxLength(200)]
        public string filePath { get; set; }

        public int month { get; set; }
    }
}
