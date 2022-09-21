using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace MSDB.Models
{
    public class work_record:tablebase
    {
        [Key]
        public int id { get; set; }

        public int customerId { get; set; }
        
        ///<summary>
        /// 服务单号
        ///</summary>
        [MaxLength(20)]
        public string serviceNo { get; set; }
        ///<summary>
        ///业务员
        ///</summary>
        public int ownerId { get; set; }
        ///<summary>
        /// 事项数
        ///</summary>
        public int itemCnt { get; set; }

        
        ///<summary>
        ///部门
        ///</summary>
        public int deptId { get; set; }
        ///<summary>
        /// 金额
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal totalAmount { get; set; }
    }
}