using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 项目文档
    /// </summary>
    public class p_document : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 项目id
        /// </summary>
        public int projectId { get; set; }

        /// <summary>
        /// 编号
        /// </summary>
        [Required,MaxLength(10)]
        public string docNumber { get; set; }

        /// <summary>
        /// 文档名
        /// </summary>
        [Required, MaxLength(30)]
        public string docName { get; set; }

        /// <summary>
        /// 文档内容
        /// </summary>
        [Required,Column(TypeName = "text")]
        public string Content { get; set; }

        /// <summary>
        /// 阶段id
        /// </summary>
        public int stageId { get; set; }
        /// <summary>
        /// 过程id
        /// </summary>
        public int processId { get; set; }
        /// <summary>
        /// 模板Id
        /// </summary>
        public int templateId { get; set; }

        public int companyId { get; set; }
    }
}
