using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace MSDB.Models
{
    public class contract_member : tablebase
    {
        [Key]
        public int id { get; set; }

        ///<summary>
        ///合同id
        ///</summary>
        public int contractId { get; set; }

        ///<summary>
        ///合作人员的mtype为其他人员
        ///<summary>
        public int mtype { get; set; }

        public int userId { get; set; }
        
        ///<summary>
        ///比例
        /// 业务员，谈单经理默认为-1
        ///</summary>
        [Required, Column(TypeName = "decimal(5,2)")]
        public decimal ratio { get; set; }
 
        [MaxLength(200)]
        public string remark { get; set; }
    }
}
