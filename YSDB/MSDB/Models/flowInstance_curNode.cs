using System;
using System.ComponentModel.DataAnnotations;

namespace MSDB.Models
{
    public class flowInstance_curNode : tablebase
    {
        [Key]
        public int id { get; set; }

        public int instanceId { get; set; }

        public int curNodeId { get; set; }

        public int nodeStatus { get; set; }

        public int flowId { get; set; }

        public int auditLevel { get; set; }

        [MaxLength(200)]
        public string auditUsers { get; set; }

        public DateTime? expired { get; set; }
        
        public bool closed { get; set; }

        public int? parentNode { get; set; }
    }
}
