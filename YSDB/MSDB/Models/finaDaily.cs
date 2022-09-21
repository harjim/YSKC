using System;
using System.ComponentModel.DataAnnotations;

namespace MSDB.Models
{
    public class finaDaily : tablebase
    {
        [Key]
        public int id { get; set; }

        public int customerId { get; set; }

        ///<summary>
        /// 业务员(customer.owerId，可修改)
        ///</summary>
        public int ownerId { get; set; }

        ///<summary>
        /// 部门id（customer.deptId,可修改）
        ///</summary>
        public int deptId { get; set; }

        ///<summary>
        ///事项(财务日报事项，字典表：type=19)
        ///</summary>
        [MaxLength(10)]
        public string itemType { get; set; }

        ///<sumamry>
        /// 工作日期
        ///<sumamry>
        public DateTime workDate { get; set; }

        ///<summary>
        /// 内容
        ///</summary>
        [Required, MaxLength(1000)]
        public string content { get; set; }

        ///<summary>
        /// 附件（多文件）
        ///</summary>
        [MaxLength(500)]
        public string filepath { get; set; }
        ///<summary>
        /// 标签：{workDate(yyyyMMdd)-itemTypeValue-companyName}
        /// 
        ///</summary>
        public string label { get; set; }
    }
}
