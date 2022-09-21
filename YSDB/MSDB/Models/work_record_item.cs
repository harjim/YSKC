using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace MSDB.Models
{
    ///<summary>
    /// 服务事项
    ///</summary>
    public class work_record_item : tablebase
    {
        [Key]
        public int id { get; set; }
        
        public int workRecordId { get; set; }

        ///<summary>
        ///事项(字典表:type=17)
        ///</summary>
        [MaxLength(10)]
        public string itemType { get; set; }
        ///<summary>
        ///开始
        ///</summary>
        public DateTime begin { get; set; }

        ///<summary>
        ///结束
        ///</summary>
        public DateTime end { get; set; }

        ///<summary>
        /// 金额
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal amount { get; set; }

        ///<summary>
        ///备注
        ///</summary>
        [MaxLength(200)]
        public string remark { get; set; }

    }
}
