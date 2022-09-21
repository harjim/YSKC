using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace EXPERTDB.Models
{
    /// <summary>
    /// 用户角色表
    /// </summary>
    public class app_user_role
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 用户ID
        /// </summary>
        public int userId { get; set; }
        /// <summary>
        /// 角色ID
        /// </summary>
        public int roleId { get; set; }
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
