using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    /// <summary>
    /// 数据权限表
    /// </summary>
    public class app_role_data: tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 角色id
        /// </summary>
        public int roleId { get; set; }
        /// <summary>
        /// 功能id
        /// </summary>
        public int functionId { get; set; }
        /// <summary>
        /// 数据类型
        /// </summary>
        public int dataType  { get; set; }

       
    }
}
