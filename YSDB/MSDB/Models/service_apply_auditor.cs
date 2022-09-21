using System;
using System.ComponentModel.DataAnnotations;

namespace MSDB.Models
{
    public class service_apply_auditor : tablebase
    {
        [Key]
        public int id { get; set; }
        public int serviceApplyId { get; set; }
        ///<summary>
        /// 技术经理
        ///</summary>
        public int? techManagerId { get; set; }
        ///<summary>
        /// 技术总监
        ///</summary>
        public int? techDirectorId { get; set; }
        ///<summary>
        /// 财务经理
        ///</summary>
        public int? finaManagerId { get; set; }
        ///<summary>
        /// 财务总监
        ///</summary>
        public int? finaDirectorId { get; set; }
        
        public int? instanceId { get; set; }
    }
}
