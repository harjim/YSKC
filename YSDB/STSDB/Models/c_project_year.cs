using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace STSDB.Models
{
    public class c_project_year : tablebase
    {
        [Key]
        public int id { get; set; }

        public int companyId { get; set; }

        public int year { get; set; }

        public int projectId { get; set; }

        ///<summary>
        ///加计金额
        ///</summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal estimateFunds { get; set; }

        ///<summary>
        ///研发金额(统计年费用)
        ///</summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal funds { get; set; }

        ///<summary>
        ///不可加计原因
        ///</summary>
        [MaxLength(500)]
        public string reason { get; set; }

        ///<summary>
        ///不可加计金额
        ///</summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal unFunds { get; set; }
    }
}
