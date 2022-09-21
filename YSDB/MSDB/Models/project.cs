using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class project : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 客户id
        /// </summary>
        public int customerId { get; set; }
        /// <summary>
        /// 年份
        /// </summary>
        public int year { get; set; }
        /// <summary>
        /// 项目类型id
        /// </summary>
        public int productId { get; set; }
        /// <summary>
        /// 业务员id
        /// </summary>
        public int owerId { get; set; }
        /// <summary>
        /// 部门id
        /// </summary>
        public int deptId { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [MaxLength(200)]
        public string remark { get; set; }
        /// <summary>
        /// 谈单经理id
        /// </summary>
        public int? businessId { get; set; }

        public int? lastLogId { get; set; }

    }
}
