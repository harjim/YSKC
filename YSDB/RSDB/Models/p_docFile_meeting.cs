using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    ///<summary>
    ///会议纪要附件表（会议纪要佐证材料）
    ///</summary>
    public class p_docFile_meeting : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 公司id
        /// </summary>
        public int companyId { get; set; }
        /// <summary>
        /// 项目ID 
        /// </summary>
        public int projectId { get; set; }
         /// <summary>
        /// 文档id 
        /// </summary>
        public int? docFileId { get; set; }
        /// <summary>
        /// 文件名称
        /// </summary>
        [Required, MaxLength(200)]
        public string fileName { get; set; }
        /// <summary>
        /// 文件路径
        /// </summary>
        [Required, MaxLength(200)]
        public string filePath { get; set; }

    }
}