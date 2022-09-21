using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class project_member_log
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 项目id
        /// </summary>
        public int projectId { get; set; }
        /// <summary>
        /// 客户id
        /// </summary>
        public int customerId { get; set; }
        /// <summary>
        /// 类型(1.技术人员;2.财务人员;3.业务员；4.谈单经理)
        /// </summary>
        public int mType { get; set; }

        ///<summary>
        /// 人员
        ///</summary>
        public int memberId { get; set; }

        ///<summary>
        /// 操作人
        ///</summary>
        public int operationId { get; set; }

        ///<summary>
        /// 操作时间
        ///</summary>
        public DateTime operationTime { get; set; }

    }
}
