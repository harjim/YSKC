using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class patent_demand_member
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 需求id
        /// </summary>
        public int deamndId { get; set; }
        /// <summary>
        /// 类型(1.技术人员;3.业务员)
        /// </summary>
        public int mType { get; set; }
        /// <summary>
        /// 人员id
        /// </summary>
        public int memberId { get; set; }
    }
}