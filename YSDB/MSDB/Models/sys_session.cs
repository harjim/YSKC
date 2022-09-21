using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class sys_session
    { 
        [Key]
        [DatabaseGenerated(System.ComponentModel.DataAnnotations.Schema.DatabaseGeneratedOption.None)]//不自动增长
        public int userId { get; set; } 
        [MaxLength(50)]
        public string token { get; set; }
        public DateTime createTime { get; set; }
        /// <summary>
        /// 更新时间
        /// </summary>
        public DateTime updateTime { get; set; }
        /// <summary>
        /// 到期时间
        /// </summary>
        public DateTime expireTime { get; set; }
    }
}
