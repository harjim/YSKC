using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 过程文件
    /// </summary>
    public class p_docFile : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 项目id
        /// </summary>
        public int projectId { get; set; }
        /// <summary>
        /// 所属阶段
        /// </summary>
        [Required, MaxLength(10)]
        public string stage { get; set; }
        /// <summary>
        /// 文档id
        /// </summary>
        public int docFileId { get; set; }
        /// <summary>
        /// 文件名
        /// </summary>
        [Required, MaxLength(50)]
        public string docFileName { get; set; }
        /// <summary>
        /// 模板id
        /// </summary>
        public int docFileTemplateId { get; set; }
        /// <summary>
        /// 排序
        /// </summary>
        public int seq { get; set; }

        public DateTime? month { get; set; }

        /// <summary>
        /// 文档日期
        /// </summary>
        public DateTime? docDate { get; set; }
        /// <summary>
        /// 是否删除
        /// </summary>
        public bool deleted { get; set; }

        /// <summary>
        /// 是否完成
        /// </summary>
        public bool finished { get; set; }
    }
}
