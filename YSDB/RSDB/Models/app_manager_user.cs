using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 服务人员表 
    /// </summary>
    public class app_manager_user
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 公司id
        /// </summary>
        public int companyId { get; set; }
        /// <summary>
        /// ms用户id
        /// </summary>
        public int msUserId { get; set; }
        /// <summary>
        /// ms部门id
        /// </summary>
        public int? msDeptId { get; set; }

        /// <summary>
        /// 用户名
        /// </summary>
        [Required, MaxLength(20)]
        public string userName { get; set; }

        /// <summary>
        /// 姓名
        /// </summary>
        [Required, MaxLength(30)]
        public string realName { get; set; }

        /// <summary>
        /// 创建人
        /// </summary>
        public int creatorId { get; set; }
        /// <summary>
        /// 创建时间
        /// </summary>
        public DateTime createTime { get; set; }
        /// <summary>
        /// 最后修改人ID
        /// </summary>
        public int lastUpdatorId { get; set; }
        /// <summary>
        /// 最后修改时间
        /// </summary>
        public DateTime lastUpdateTime { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [MaxLength(200)]
        public string remark { get; set; }

    }
}
