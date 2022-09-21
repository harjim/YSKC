using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 检测维修表
    /// </summary>
    public class d_inspection : tablebase
    {
        [Key]
        public int id { get; set; }

        /// <summary>
        /// 记帐日期
        /// </summary>
        public DateTime accDate { get; set; }
        /// <summary>
        /// 凭证字号
        /// </summary>
        [Required, MaxLength(100)]
        public string accNumber { get; set; }

        /// <summary>
        /// 检测维修费用
        /// </summary>
        public decimal expense { get; set; }

        /// <summary>
        /// 摘要
        /// </summary>
        [Required, MaxLength(120)]
        public string summary { get; set; }

        /// <summary>
        /// 0：检测，1：维修，2: 摊消 3： 试制，4：其他
        /// </summary>
        public int type { get; set; }
        /// <summary>
        /// 部门Id, 如果没有部门，给当前公司根部门
        /// </summary>
        public int? deptId { get; set; }

        public int companyId { get; set; }


        [MaxLength(200)]
        public string remark { get; set; }

        /// <summary>
        /// 研发部门
        /// </summary>
        public int? rdDeptId { get; set; }

        /// <summary>
        /// 部门
        /// </summary>
        [MaxLength(100)]
        public string deptName { get; set; }

        /// <summary>
        /// 员工编号/标识，差旅费有用
        /// </summary>
        [MaxLength(30)]
        public string enumber { get; set; }
        /// <summary>
        /// 关联科目id
        /// </summary>
        public int accountTitleId { get; set; }


        /// <summary>
        /// 剩余设计费用
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal remainExpense { get; set; }

    }
}
