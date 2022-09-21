using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class t_project_list : tablebase
    {
        [Key]
        public int id { get; set; }
        [MaxLength(500)]
        public string filePath { get; set; }

        public int stageListId { get; set; }

        [MaxLength(200)]
        public string fileName { get; set; }

        public int projectId { get; set; }

        public int companyId { get; set; }
    }
}