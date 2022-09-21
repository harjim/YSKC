using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class project_audit_summary
    {

        [Key]
        public int id { get; set; }
        public int? projectId { get; set; }
        ///<summary>
        ///完成数
        ///</summary>
        public int doneCnt { get; set; }
        ///<summary>
        ///进行数（提交数）
        ///</summary>
        public int ongoingCnt { get; set; }

        public DateTime createTime { get; set; }
        public DateTime lastUpdateTime { get; set; }

        ///<summary>
        /// 1：按年审核（研发花名册，设备，研发架构，项目等）,
        /// 2：文档，3：按项目提交（原查新报告）,4：专利交底书
        ///</summary>
        public int modeType { get; set; }

        public int customerId { get; set; }
        public int year { get; set; }
        ///<summary>
        /// 0：技术，1：财务
        ///</summary>
        public int auditType { get; set; }

    }
}