using System;
using System.ComponentModel.DataAnnotations;

namespace MSDB.Models
{
    public class service_apply_customer : tablebase
    {
        [Key]
        public int id { get; set; }

        public int serviceApplyId { get; set; }

        ///<summary>
        /// 客户id
        ///</summary>
        public int customerId { get; set; }

        ///<summary>
        /// 事由
        ///</summary>
        [Required, MaxLength(200)]
        public string causes { get; set; }
    }
}
