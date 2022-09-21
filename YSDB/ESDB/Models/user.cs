using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace ESDB.Models
{
    public class user
    {
        [Key]
        public int id { get; set; }
        public DateTime createTime { get; set; }
        public DateTime lastUpdateTime { get; set; }
        ///<summary>
        /// 用户名
        ///</summary>
        [Required, MaxLength(20)]
        public string username { get; set; }
        [Required, MaxLength(50)]
        ///<summary>
        /// 密码 md5（username+password）
        ///</summary>
        public string password { get; set; }
        [MaxLength(30)]
        public string realname { get; set; }
        ///<summary>
        /// 手机号码
        ///</summary>
        [MaxLength(30)]
        public string mobile { get; set; }
        ///<summary>
        /// 邮箱
        ///</summary>
        [MaxLength(50)]
        public string email { get; set; }
        ///<summary>
        /// 出生日期
        ///</summary>
        public DateTime? birthday { get; set; }

        /// <summary>
        ///状态: 1：禁用  0 启用(默认为0)
        /// </summary>
        public bool disabled { get; set; }
        ///<summary>
        /// 头像
        ///</summary>
        [MaxLength(200)]
        public string avatar { get; set; }
        ///<summary>
        /// 1男，2 女
        ///</summary>
        public byte? gender { get; set; }

        ///<summary>
        ///证件类型 0 身份证，1护照 2 港澳通行证
        ///</summary>
        public int? idType { get; set; }
        [MaxLength(30)]
        public string idNo { get; set; }
        ///<summary>
        /// 备注
        ///</summary>
        [MaxLength(200)]
        public string remark { get; set; }

        ///<summary>
        /// 用户类型 默认为0
        ///</summary>
        [MaxLength(20)]
        public string types { get; set; }

    }
}