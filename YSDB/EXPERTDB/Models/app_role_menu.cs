using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace EXPERTDB.Models
{
    public class app_role_menu
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 角色ID
        /// </summary>
        public int roleId { get; set; }
        /// <summary>
        /// 菜单id
        /// </summary>
        public int menuId { get; set; }

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
