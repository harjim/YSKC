using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;
namespace MSDB.Models
{
    public class project_tech_stage : tablebase
    {
        [Key]
        public int id { get; set; }

        public int stageId { get; set; }

        public DateTime deadline { get; set; }

        public int projectId { get; set; }

    }
}