using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class patent_plan_process
    {
        [Key]
        public int id { get; set; }

        public int patentPlanId { get; set; }

        ///<summary>
        /// 0：技术交底，1：交底审批，2：代理人分配，3：定稿，4：受理，5:受理缴费，6：授权，7授权缴费
        ///<summary>
        public int process { get; set; }

        public int userId { get; set; }
        public DateTime createTime { get; set; }
        [MaxLength(200)]
        public string remark { get; set; }

    }
}