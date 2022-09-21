using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace STSDB.Models
{
    public class c_project_funds : tablebase
    {
        [Key]
        public int id { get; set; }

        public int companyId { get; set; }

        public int projectId { get; set; }

        public DateTime month { get; set; }

        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal funds { get; set; }
    }
}
