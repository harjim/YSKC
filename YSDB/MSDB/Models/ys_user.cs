using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    /// <summary>
    /// 优赛用户表
    /// </summary>
    public class ys_user
    {
        [Key]
        public int id { get; set; }
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
        /// 部门Id
        /// </summary>
        public int depId { get; set; }

        /// <summary>
        /// 职位
        /// </summary>
        [MaxLength(20)]
        public string postion { get; set; }
        /// <summary>
        /// 0,启用 1,禁用
        /// </summary>
        public byte status { get; set; }

        /// <summary>
        /// 用户头像
        /// </summary>
        [MaxLength(20)]
        public string avatar { get; set; }

        [MaxLength(300)]
        public string remark { get; set; }

        /// <summary>
        /// 创建id
        /// </summary>
        public int creatorId { get; set; }
        /// <summary>
        /// 创建时间
        /// </summary>
        public DateTime createTime { get; set; }
        /// <summary>
        /// 是否重置了密码
        /// </summary>
        [DefaultValue(false)]
        public bool reSetPassword { get; set; }
        [MaxLength(50)]
        public String token { get; set; }

        /// <summary>
        /// 是否同步钉钉数据,false表示同步
        /// </summary>
        [DefaultValue(false)]
        public bool notSyndd { get; set; }

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
        ///<sumamry>
        /// 管理类型： 1.技术人员;2.财务人员;3.业务员；4.谈单经理(可多个类型)
        ///</summary>
        [MaxLength(20)]
        public string mtypes { get; set; }
        /// <summary>
        /// rs最后操作时间
        /// </summary>
        public DateTime? rsLastOperationTime { get; set; }
    }
}
