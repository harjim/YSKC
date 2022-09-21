using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 设计费用
    /// </summary>
    public class d_design : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 设计名称
        /// </summary>
        [Required, MaxLength(100)]
        public string dname { get; set; }
        /// <summary>
        /// 设计日期
        /// </summary>
        public DateTime designDate { get; set; }
        /// <summary>
        /// 设计费用
        /// </summary>
        public decimal dFee { get; set; }

        public int companyId { get; set; }


        [MaxLength(200)]
        public string remark { get; set; }
        /// <summary>
        /// 研发部门
        /// </summary>
        public int? rdDeptId { get; set; }
        /// <summary>
        /// 所属部门
        /// </summary>
        public int? deptId { get; set; }

        /// <summary>
        /// 部门
        /// </summary>
        [MaxLength(100)]
        public string deptName { get; set; }

        /// <summary>
        /// 关联科目id
        /// </summary>
        public int accountTitleId { get; set; }

        /// <summary>
        /// 剩余设计费用
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal remainDFee { get; set; }
    }
}
