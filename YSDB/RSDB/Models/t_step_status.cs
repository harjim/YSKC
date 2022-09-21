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
    public class t_step_status
    { 
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 状态 0未提交 1 提交 2审核通过 3撤回
        /// </summary>
        public int status { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
          [MaxLength(500)]
        public String remark { get; set; }
        /// <summary>
        /// 审核人
        /// </summary>
        public int auditorId { get; set; }
        /// <summary>
        /// 审核时间
        /// </summary>
        public DateTime auditTime { get; set; }
        /// <summary>
        /// 项目类型id
        /// </summary>
        public int productId { get; set; }
    }
}