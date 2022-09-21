using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 研发花名册
    /// </summary>
    public class rdEmployee : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 年份
        /// </summary>
        public short year { get; set; }
        /// <summary>
        /// 公司ID
        /// </summary>
        public int companyId { get; set; }
        /// <summary>
        /// 员工编号/标识
        /// </summary>
        [Required, MaxLength(30)]
        public string enumber { get; set; }

        /// <summary>
        /// 员工类型，0：普通员工， 1： 研究人员， 2：技术人员 3：辅助人员
        /// </summary>
        public int etype { get; set; }
        /// <summary>
        /// 研发部门
        /// </summary>
        public int rdDeptId { get; set; }

           /// <summary>
        /// 职位
        /// </summary>
        [ MaxLength(20)]
        public String position { get; set; }

    }
}
