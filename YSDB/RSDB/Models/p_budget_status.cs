using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class p_budget_status
    {
        [Key]
        public int id { get; set; }

        public int companyId { get; set; }
        public int projectId { get; set; }
        public int status { get; set; }
        public int msCreatorId { get; set; }
        public int msLastUpdatorId { get; set; }
        public DateTime lastUpdateTime { get; set; }
        public DateTime createTime { get; set; }
    }
}