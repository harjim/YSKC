using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 技改项目附件
    /// </summary>
    public class t_projectAppendix : tablebase
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
        /// 下载次数
        /// </summary>
        public int downloadTimes { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [MaxLength(200)]
        public string remark { get; set; }
        /// <summary>
        /// 附件类型 
        /// </summary>
        public int type { get; set; }
    }
}
