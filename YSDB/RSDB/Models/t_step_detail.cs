using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 步骤状态表
    /// </summary>
    public class t_step_detail: tablebase
    { 
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 公司id
        /// </summary>
        public int companyId { get; set; }
          /// <summary>
        /// 项目类型id
        /// </summary>
        public int productId { get; set; }
        /// <summary>
        /// 项目id
        /// </summary>
        public int projectId { get; set; }
        /// <summary>
        /// 上传步骤id
        /// </summary>
        public int stepId { get; set; }
        /// <summary>
        /// 文件名称
        /// </summary>
          [MaxLength(500)]
        public String fileName { get; set; }
        /// <summary>
        /// 文件路径
        /// </summary>
         [MaxLength(500)]
        public String filePath { get; set; }
      
    }
}