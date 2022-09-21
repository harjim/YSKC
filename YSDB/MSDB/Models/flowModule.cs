using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;
namespace MSDB.Models
{
    public class flowModule
    {
        [Key]
        public int id { get; set; }
        public int moduleId { get; set; }
        public int flowId { get; set; }
        [Required, MaxLength(50)]
        public string modeName { get; set; }
        ///<summary>
        /// 0：项目审核
        ///</summary>
        public int type { get; set; }
        ///<summary>
        /// 审核类型：0技术审核，1财务审核
        ///</summary>
        public int auditType { get; set; }

        public DateTime createTime { get; set; }
        public int seq { get; set; }
    }
}