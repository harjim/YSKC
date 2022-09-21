using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class user : tablebase
    {
        [Key]
        public int id { get; set; }

        public int companyId { get; set; }
        /// <summary>
        /// 用户名
        /// </summary>
        [Required, MaxLength(20),]
        public string userName { get; set; }
        /// <summary>
        /// 密码
        /// </summary>
        [Required, MaxLength(50)]
        public string password { get; set; }

        /// <summary>
        /// 姓名
        /// </summary>
        [Required, MaxLength(30)]
        public string realName { get; set; }

        /// <summary>
        /// 0: 未知 1： 男 2： 女
        /// </summary>
        public byte gender { get; set; }

        /// <summary>
        /// 电话
        /// </summary>
        [Required, MaxLength(30)]
        public string tel { get; set; }

        /// <summary>
        /// 0,启用  1 禁用
        /// </summary>
        public byte status { get; set; }
        /// <summary>
        /// 帐户类型，0：主帐户，1：子帐户
        /// </summary>
        public byte type { get; set; }

        /// <summary>
        /// 用户头像
        /// </summary>
        [MaxLength(20)]
        public string avatar { get; set; }

        [MaxLength(300)]
        public string remark { get; set; }

        /// <summary>
        /// 邮箱
        /// </summary>
        [MaxLength(100)]
        public string email { get; set; }

        /// <summary>
        /// 角色类型：0：工厂，1：区域
        /// </summary>
        public int roleType { get; set; }
    }
}
