using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace EXPERTDB.Models
{

    public class app_role
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 角色名
        /// </summary>
        [Required, MaxLength(20)]
        public string roleName { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [MaxLength(100)]
        public string remark { get; set; }

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
