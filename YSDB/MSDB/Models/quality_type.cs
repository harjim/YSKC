using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace MSDB.Models
{
    public class quality_type : tablebase
    {
        [Key]
        public int id { get; set; }
        ///<summary>
        /// 权重
        ///</summary>
        [Column(TypeName = "decimal(5,3)")]
        public decimal weight { get; set; }
        ///<summary>
        /// 类型：0 立项报告
        ///</summary>
        public int type { get; set; }     
    }
}