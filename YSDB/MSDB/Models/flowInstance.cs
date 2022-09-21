using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class flowInstance : tablebase
    {
        [Key]
        public int id { get; set; }
        public int? curNodeId { get; set; }
        public int flowId { get; set; }

        ///<summary>
        /// 0：进行中 1:通过 2：驳回 3:激活
        ///</summary>
        public int status { get; set; }

        ///<summary>
        /// 0进行中 1通过 2驳回 3激活 4提交 5未提交 6失败 7提交失败
        ///</summary>
        public int? nodeStatus { get; set; }

        public int moduleId { get; set; }
        public int deptId { get; set; }

        public int lastSubmiter { get; set; }

        public int? companyId { get; set; }

        ///<summary>
        ///内容明细  
        ///</summary>
        [MaxLength(200)]
        public string content { get; set; }

        ///<summary>
        /// 审核层级（连续多级主管审核-当前审核级数）
        public int? auditLevel { get; set; }

        ///<summary>
        ///当前审核用户
        ///</summary>
        [MaxLength(200)]
        public string auditUsers { get; set; }
        ///<summary>
        ///截至日期，null为无期限
        ///</summary>
        public DateTime? expired { get; set; }

        ///<summary>
        /// 可撤回 (提交后默认为true，审核后置为false)
        ///<summary>
        public bool revokable{ get; set; }
    }
}