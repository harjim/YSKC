using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 技改申请报告
    /// </summary>
    public class t_declaration : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 项目id，t_project
        /// </summary>
        public int projectId { get; set; }
        /// <summary>
        /// 公司id
        /// </summary>
        public int companyId { get; set; }
        /// <summary>
        /// 排序
        /// </summary>
        public int seq { get; set; }

        [Required, MaxLength(30)]
        public string key { get; set; }
        [Required, MaxLength(30)]
        public string parentKey { get; set; }
        /// <summary>
        /// 标题
        /// </summary>
        [Required, MaxLength(300)]
        public string title { get; set; }
        /// <summary>
        /// 内容
        /// </summary>
        public string content { get; set; }
    }
}
