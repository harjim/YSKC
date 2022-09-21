using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{

    public class app_role
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 公司ID，0：表示所有公司适用
        /// </summary>
        public int companyId { get; set; }
        /// <summary>
        /// 角色名
        /// </summary>
        [Required, MaxLength(20)]
        public string roleName { get; set; }
        /// <summary>
        /// 当前角色所属人员类型,为MS端用户跳转至RS端提供通用权限用,与MS端项目成员类型一致.
        /// </summary>
        public int? mType { get; set; }
        /// <summary>
        /// 当前角色所属项目类型,为MS端用户跳转至RS端提供通用权限用,与MS端项目类型一致.
        /// </summary>
        public int? productType { get; set; }
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
