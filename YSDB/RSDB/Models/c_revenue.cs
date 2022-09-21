using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace RSDB.Models
{
    ///<summary>
    /// 年度营收管理
    ///</summary>
    public class c_revenue : tablebase
    {
        [Key]
        public int id { get; set; }
        public int companyId { get; set; }
        public int year { get; set; }
        public DateTime month { get; set; }
        
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal revenue { get; set; }
    }
}