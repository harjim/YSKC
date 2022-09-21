using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    /// <summary>
    /// 钉钉用户关联表
    /// </summary>
    public class d_user
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// ms系统用户id
        /// </summary>
        public int userId { get; set; }
        /// <summary>
        /// 钉钉用户id，员工在当前企业内的唯一标识，也称staffId。可由企业在创建时指定，并代表一定含义比如工号，创建后不可修改
        /// </summary>
        [Required, MaxLength(100)]
        public string dingUserId { get; set; }
        /// <summary>
        /// 钉钉员工在当前开发者企业账号范围内的唯一标识，系统生成，固定值，不会改变
        /// </summary>
        [Required, MaxLength(100)]
        public string unionid { get; set; }
        /// <summary>
        ///  用户在当前开放应用内的唯一标识 暂时不要管
        /// </summary>
        [Required, MaxLength(100)]
        public string openid { get; set; }
    }
}
