using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class p_project_state
    {
        [Key]
        public int id { get; set; }
        public int companyId { get; set; }
        public int projectId { get; set; }
        ///<summary>
        /// 结项状态：0 未结项 ，1 已结项
        ///</summary>
        public int status { get; set; }
        public int msCreatorId { get; set; }
        public int msLastUpdatorId { get; set; }
        public DateTime createTime { get; set; }
        public DateTime lastUpdateTime { get; set; }
        [MaxLength(1000)]
        public string comment { get; set; }
    }
}