using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 过程文件
    /// </summary>
    public class p_docFile_data : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 文档id（p_docFile）
        /// </summary>
        public int pdocFileId { get; set; }
        /// <summary>
        /// 数据
        /// </summary>
        [Required, Column(TypeName = "text")]
        public string data { get; set; }
        /// <summary>
        /// 字长
        /// </summary>
        public int wordLength { get; set; }
        /// <summary>
        /// 已填项数
        /// </summary>
        public int filledItems { get; set; }
        /// <summary>
        /// 总项数
        /// </summary>
        public int totalItems { get; set; }

        /// <summary>
        /// 内容汉明码
        /// </summary>
         [MaxLength(128)]
        public String simCode { get; set; }

    }
}
