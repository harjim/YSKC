using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace MSDB.Models
{
    ///<summary>
    /// 查新费用
    ///</summary>
    public class checkInst_price : tablebase
    {
        [Key]
        public int id { get; set; }
        ///<summary>
        /// 查新机构ID
        ///</summary>
        public int checkInstId { get; set; }
        ///<summary>
        /// 天数(下一条数据的days不能小于当前的days)
        ///</summary>
        public int days { get; set; }
        ///<summary>
        /// 金额（元）
        ///</summary>
        [Column(TypeName="decimal(18,2)")]
        public decimal amount { get; set; }
        ///<summary>
        /// 查新类型(0:国内查新 1:国内外查新 2:国外查新)
        ///</summary>
        public int checkType { get; set; }

    }
}
