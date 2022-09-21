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
    public class patent_account : tablebase
    {
        [Key]
        public int id { get; set; }


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
        ///状态： 1 未使用，2可使用，3，不可用
        ///</summary>
        public int status { get; set; }

        ///<summary>
        ///备注
        ///</summary>
        [MaxLength(200)]
        public string remark { get; set; }
    }
}
