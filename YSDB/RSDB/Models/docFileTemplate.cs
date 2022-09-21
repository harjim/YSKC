using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 过程文档模板表
    /// </summary>
    public class docFileTemplate : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 文档id
        /// </summary>
        public int docFileId { get; set; }
        /// <summary>
        /// 模板名称
        /// </summary>
        [Required, MaxLength(25)]
        public string templateName { get; set; }
         /// <summary>
        /// 是否必选
        /// </summary>
        public bool defaultTemplate { get; set; }
        /// <summary>
        /// 过程文件模板名称
        /// </summary>
        [Required, MaxLength(25)]
        public string docTemplateName { get; set; }
          /// <summary>
        /// 是否启用
        /// </summary>
        public bool enabled { get; set; }
          /// <summary>
        /// 模板类型：0普通 1 委托
        /// </summary>
        public int type { get; set; }

    }
}
