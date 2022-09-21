using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 系统日志
    /// </summary>
    public class sys_log
    {
        [Key]
        public int id { get; set; }
        [MaxLength(100), Required]
        public string description { get; set; }
        public int logType { get; set; }
        public int userId { get; set; }
        [MaxLength(100), Required]
        public string username { get; set; }
        /// <summary>
        /// 0 rs, 1 ms
        /// </summary>
        public int source { get; set; }
        public DateTime logTime { get; set; }
        [MaxLength(100), Required]
        public string logUrl { get; set; }
        [MaxLength(4000)]
        public string logParams { get; set; }

        [MaxLength(200)]
        public string remark { get; set; }
        [MaxLength(30)]
        public string requestIp { get; set; }
        public int companyId { get; set; }
        [MaxLength(50)]
        public String companyName { get; set; }
    }
}
