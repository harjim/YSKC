using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    /// <summary>
    /// 用户部门关联表，多对多关系
    /// </summary>
    public class user_dept
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// ms系统用户id
        /// </summary>
        public int userId { get; set; }
        /// <summary>
        /// 部门id
        /// </summary>
        public int depId { get; set; }

        public bool isAdmin { get; set; }

        [Required, MaxLength(100)]
        [DefaultValue("")]
        public string unionid { get; set; }

        /// <summary>
        /// 钉钉用户id，员工在当前企业内的唯一标识，也称staffId。可由企业在创建时指定，并代表一定含义比如工号，创建后不可修改
        /// </summary>
        [Required, MaxLength(100)]
        [DefaultValue("")]
        public string dingUserId { get; set; }
    }
}
