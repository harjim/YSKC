using System;
using System.ComponentModel.DataAnnotations;

namespace STSDB.Models
{
    public class o_user : tablebase
    {
        [Key]
        public int id { get; set; }

        ///<summary>
        /// 用户名
        ///</summary>
        [Required, MaxLength(20)]
        public string username { get; set; }

        ///<summary>
        /// 密码 md5（username+password）
        ///</summary>
        [Required, MaxLength(50)]
        public string password { get; set; }

        [Required, MaxLength(50)]
        public string realName { get; set; }

        ///<summary>
        /// 状态：0：启用，1：禁用
        ///</summary>
        public int status { get; set; }

        public int orgId { get; set; }

        [MaxLength(200)]
        public string remark { get; set; }
        [MaxLength(50)]
        public string token { get; set; }
        [MaxLength(20)]
        public string position { get; set; }
        [MaxLength(30)]
        public string tel { get; set; }
        [MaxLength(50)]
        public string email { get; set; }
        [MaxLength(50)]
        public string deptName { get; set; }
    }
}
