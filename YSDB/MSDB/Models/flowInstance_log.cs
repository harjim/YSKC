using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class flowInstance_log
    {
        [Key]
        public int id { get; set; }
        public int instanceId { get; set; }
        /// <summary>
        /// 审核状态 1通过 2驳回 3激活
        /// </summary>
        public int status { get; set; }
        [MaxLength(2000)]
        public string suggestion { get; set; }
        public int msCreatorId { get; set; }
        public DateTime createTime { get; set; }
        public int nodeId { get; set; }
        public int? submiter { get; set; }
        public bool lastNode { get; set; }
    }
}