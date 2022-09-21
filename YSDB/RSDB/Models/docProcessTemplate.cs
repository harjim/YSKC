using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 过程模板配置
    /// </summary>
    public class docProcessTemplate
    {
        [Key]
        public int id { get; set; }

        /// <summary>
        /// 过程id
        /// </summary>
        public int processId { get; set; }
        /// <summary>
        /// 序号
        /// </summary>
        public int seq { get; set; }
        /// <summary>
        /// 模板Id
        /// </summary>
        public int templateId { get; set; }
        
        /// <summary>
        /// 是否可选
        /// </summary>
        public bool optional { get; set; }

        /// <summary>
        /// 是否可以多个
        /// </summary>
        public bool multiple { get; set; }

        /// <summary>
        /// 是否启用.
        /// </summary>
        public bool enabled { get; set; }
    }
}
