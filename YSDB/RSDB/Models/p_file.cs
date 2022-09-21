using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 项目文件表
    /// </summary>
    public class p_file:tablebase
    {
        [Key]
        public int id { get; set; }
        public int projectId { get; set; }
        public int companyId { get; set; }
        /// <summary>
        /// 文件名
        /// </summary>
        [Required, MaxLength(100)]
        public string fileName { get; set; }
        /// <summary>
        /// 文件路径
        /// </summary>
        [Required, MaxLength(200)]
        public string filePath { get; set; }

        /// <summary>
        /// 1:费用总结报告
        /// </summary>
        public int type { get; set; }

    }
}
