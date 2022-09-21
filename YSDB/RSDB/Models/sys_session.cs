using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class sys_session
    {
        [Key]
        public int id { get; set; }

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
        /// <summary>
        /// 用户来源，0：默认本系统，1：管理系统
        /// </summary>
        public int source { get; set; }
        /// <summary>
        /// 公司ID
        /// </summary>
        public int companyId { get; set; }
    }
}
