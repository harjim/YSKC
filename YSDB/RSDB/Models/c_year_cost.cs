using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace RSDB.Models
{
    ///<summary>
    /// 年度成本管理
    ///</summary>
    public class c_year_cost : tablebase
    {
        [Key]
        public int id { get; set; }
        public int companyId { get; set; }
        public int year { get; set; }
        public int? month { get; set; }
        public int rdType { get; set; }
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal cost { get; set; }
    }
}