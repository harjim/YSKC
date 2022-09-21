using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class innovation_member
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 类型(1.技术人员;2.财务人员;3.业务员；4.谈单经理)
        /// </summary>
        public int mType { get; set; }
        /// <summary>
        /// 人员id
        /// </summary>
        public int memberId { get; set; }
        /// <summary>
        /// 是否负责人(财务/技术只能有一个负责人，业务员没有负责人)
        /// </summary>
        public Boolean isMaster { get; set; }
        /// <summary>
        /// 创新项目id
        /// </summary>
        public int innovationId { get; set; }
        public int creatorId {get;set;}
        public DateTime createTime{get;set;}

    }
}
