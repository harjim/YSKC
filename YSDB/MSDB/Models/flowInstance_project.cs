using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class flowInstance_project : tablebase
    {
        [Key]
        public int id { get; set; }
        public int year { get; set; }
        public int companyId { get; set; }
        public int projectId { get; set; }
        public int instanceId { get; set; }
        public int moduleId { get; set; }

    }
}