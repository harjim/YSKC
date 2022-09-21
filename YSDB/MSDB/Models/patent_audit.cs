using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class patent_audit : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 公司id
        /// </summary>
        public int companyId { get; set; }
        /// <summary>
        /// 专利申请id
        /// </summary> 
        public int patentPlanId { get; set; }
        /// <summary>
        /// 模块id
        /// </summary> 
        public int moduleId { get; set; }
        /// <summary>
        /// 审核状态 0进行中 1通过 2驳回 3激活 4提交 5(null)未提交 6失败 7提交失败 [当且仅当 status=1，patent_plan_process表的process 可进行 0(审批) 之后的操作]
        /// </summary> 
        public int status { get; set; }

    }
}