using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class project_member : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 项目id
        /// </summary>
        public int projectId { get; set; }
        /// <summary>
        /// 类型(1.技术人员;2.财务人员;3.业务员；4.谈单经理)
        /// </summary>
        public int mType { get; set; }
        /// <summary>
        /// 人员id
        /// </summary>
        public int memberId { get; set; }
        /// <summary>
        /// 客户Id,从项目表冗余.
        /// </summary>
        public int customerId { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [MaxLength(200)]
        public string remark { get; set; }

    }
}
