using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 文档模板表
    /// </summary>
    public class docTemplate
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        [Required, MaxLength(30)]
        public string docName { get; set; }
        /// <summary>
        /// 版本
        /// </summary>
        [Required, MaxLength(15)]
        public string version { get; set; }

        [Required,Column(TypeName = "text")]
        [Description("内容")]
        public string content { get; set; }
        /// <summary>
        /// 模板名，对应的VUE组件名
        /// </summary>
        [Required, MaxLength(50)]
        public string templateName { get; set; }
        /// <summary>
        /// 模板路径
        /// </summary>
        [Required, MaxLength(100)]
        [DefaultValue("")]
        public string templatePath { get; set; }

        /// <summary>
        /// 创建人
        /// </summary>
        public int msCreatorId { get; set; }
        /// <summary>
        /// 创建时间
        /// </summary>
        public DateTime createTime { get; set; }
        /// <summary>
        /// 最后操作人id
        /// </summary>
        public int lastMsUpdatorId { get; set; }
        /// <summary>
        /// 最后更新时间
        /// </summary>
        public DateTime lastUpdateTime { get; set; }
    }
}
