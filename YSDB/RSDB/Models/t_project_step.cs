using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 项目专利表
    /// </summary>
    public class t_project_step : tablebase
    { 
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 父节点id
        /// </summary>
        public int parentId { get; set; }
        /// <summary>
        /// 步骤名称
        /// </summary>
          [Required, MaxLength(100)]
        public String stepName { get; set; }
        /// <summary>
        /// 步骤类型
        /// </summary>
        public int stepType { get; set; }
        /// <summary>
        /// 序号
        /// </summary>
        public int seq { get; set; }
        /// <summary>
        /// 项目类型id
        /// </summary>
        public int productId { get; set; }
    }
}