using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class rsProject_master
    {
        [Key]
        public int id { get; set; }
        public int companyId { get; set; }
        public int year { get; set; }
        /// <summary>
        /// 类型(1.技术人员;2.财务人员)
        /// </summary>
        public int mType { get; set; }
        public int rsProjectId { get; set; }
        public int userId { get; set; }
        public DateTime createTime { get; set; }
    }
}