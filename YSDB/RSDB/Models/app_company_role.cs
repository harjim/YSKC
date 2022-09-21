using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 公司角色表,用于分配一个公司所拥有的角色。
    /// </summary>
    public class app_company_role : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 角色ID
        /// </summary>
        public int roleId { get; set; }
        /// <summary>
        /// 公司ID
        /// </summary>
        public int companyId { get; set; }
    }
}
