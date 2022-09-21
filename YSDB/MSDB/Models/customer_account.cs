using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    /// <summary>
    /// 客户账户
    /// </summary>
    public class customer_account : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 客户id
        /// </summary>
        public int customerId { get; set; }
        /// <summary>
        /// 系统名称
        /// </summary>
        [Required, MaxLength(100)]
        public string platform { get; set; }
        /// <summary>
        /// 网址
        /// </summary>
        [Required, MaxLength(200)]
        public string pUrl { get; set; }
        ///<summary>
        ///用户名
        ///</summary>
        [Required, MaxLength(50)]
        public string username { get; set; }
        ///<summary>
        ///密码
        ///</summary>
        [Required, MaxLength(30)]
        public string password { get; set; }
        ///<summary>
        ///备注
        ///</summary>
        [MaxLength(200)]
        public string remark { get; set; }
    }
}
