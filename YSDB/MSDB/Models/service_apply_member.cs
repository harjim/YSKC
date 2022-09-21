using System;
using System.ComponentModel.DataAnnotations;

namespace MSDB.Models
{
    public class service_apply_member : tablebase
    {
        [Key]
        public int id { get; set; }

        public int serviceApplyId { get; set; }
        ///<summary>
        /// 类型：1.技术人员;2.财务人员;5.其他
        ///</summary>
        public int mtype { get; set; }

        ///<summary>
        /// 用户id
        ///</summary>
        public int userId { get; set; }
    }
}
