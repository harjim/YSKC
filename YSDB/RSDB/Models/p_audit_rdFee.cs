using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 归集费用审核
    /// </summary>
    public class p_audit_rdFee
    {
         [Key]
        public int id { get; set; }
        public int companyId { get; set; }
        public DateTime month { get; set; }
        public int projectId { get; set; }
        public int rdType { get; set; }
        public int moduleId { get; set; }
        public int status { get; set; }
        public int msCreatorId { get; set; }
        public int msLastUpdatorId { get; set; }
        public DateTime lastUpdateTime { get; set; }
        public DateTime createTime { get; set; }
        [MaxLength(2000)]
        public string suggestion { get; set; }

    }
}