using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class project_tech_log : tablebase
    {
        [Key]
        public int id { get; set; }

        public int projectId { get; set; }

        ///<summary>
        ///技术人员id
        ///</summary>
        public int techId { get; set; }

        [Required, MaxLength(200)]
        public string process { get; set; }

        [Required, MaxLength(200)]
        public string feedback { get; set; }
        [MaxLength(10)]
        public String stageKey { get; set; }
        public int status { get; set; }
        [Required, MaxLength(10)]
        public string labelKey { get; set; }

        [MaxLength(200)]
        public string remark { get; set; }
      
        [Column(TypeName = "decimal(18,2)")]
        public decimal budget { get; set; }

        public DateTime? completedDate { get; set; }

    }
}