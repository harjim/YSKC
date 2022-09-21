using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class district_summary
    {
        [Key]
        public int id { get; set; }

        public int deptId { get; set; }

        public int year { get; set; }

        [Column(TypeName = "decimal(18,2)")]
        public decimal rdFunds { get; set; }

        public DateTime createTime { get; set; }

        public DateTime updateTime { get; set; }
    }
}