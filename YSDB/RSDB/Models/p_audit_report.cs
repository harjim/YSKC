using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 查新报告审核
    /// </summary>
    public class p_audit_report
    {
        [Key]
        public int id { get; set; }
        public int companyId { get; set; }
        public int projectId { get; set; }

        public int moduleId { get; set; }
        /// <summary>
        /// 0进行中 1通过 2驳回 3激活 4提交 5未提交 
        /// </summary>
        public int status { get; set; }
        public int msCreatorId { get; set; }
        public int msLastUpdatorId { get; set; }
        public DateTime createTime { get; set; }
        public DateTime lastUpdateTime { get; set; }
          /// <summary>
        /// 项目审核意见
        /// </summary>
        [MaxLength(500)]
        public string suggestion { get; set; }
    }
}