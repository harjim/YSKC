using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class pushAudit
    {
        [Key]
        public int id { get; set; }
        public int instanceId { get; set; }
        public int nodeId { get; set; }
        [MaxLength(1000)]
        public string msg { get; set; }
        [MaxLength(600)]
        public string sendUserIds { get; set; }
        public DateTime createTime { get; set; }
        public int status { get; set; }
    }
}