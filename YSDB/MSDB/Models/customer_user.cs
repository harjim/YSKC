using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    /// <summary>
    /// 客户人员表
    /// </summary>
    public class customer_user : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 客户id
        /// </summary>
        public int customerId { get; set; }
        /// <summary>
        /// 人员id
        /// </summary>
        public int userId { get; set; }
        /// <summary>
        /// 人员类型 1:技术人员，2：财务人员，3：业务员，4：谈单经理
        /// </summary>
        public int mType { get; set; }
        ///<summary>
        ///备注
        ///</summary>
        [MaxLength(200)]
        public string remark { get; set; }

    }
}
