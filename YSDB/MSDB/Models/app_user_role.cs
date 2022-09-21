using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    /// <summary>
    /// 用户角色表
    /// </summary>
    public class app_user_role
    {
        [Key]
        public int id { get; set; }
        public int userId { get; set; }

        public int roleId { get; set; }

        /// <summary>
        /// 服务部门,当前用户角色所服务的部门,主要用来做数据权限
        /// </summary>
        [MaxLength(300)]
        public string serviceDeptIds { get; set; }

        /// <summary>
        /// 创建id
        /// </summary>
        public int creatorId { get; set; }
        /// <summary>
        /// 创建时间
        /// </summary>
        public DateTime createTime { get; set; }
    }
}
