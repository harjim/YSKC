using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 能源损耗表
    /// </summary>
    public class d_energy : tablebase
    {
        [Key]
        public int id { get; set; }

        /// <summary>
        /// 能源名称
        /// </summary>
        [Required, MaxLength(100)]
        public string ename { get; set; }
        /// <summary>
        /// 能源类型，1:动力 2：燃料
        /// </summary>
        public int type { get; set; }
        /// <summary>
        /// 发生日期
        /// </summary>
        public DateTime occDate { get; set; }
        /// <summary>
        /// 单价
        /// </summary>
        public decimal unitPrice { get; set; }

        /// <summary>
        /// 数量，考虑会有0.5吨之类的情况
        /// </summary>
        public decimal quantity { get; set; }
        /// <summary>
        /// 剩余的能源
        /// </summary>
        public decimal remainAmount { get; set; }
        /// <summary>
        /// 单位
        /// </summary>
        [MaxLength(10)]
        public string unit { get; set; }

        public int companyId { get; set; }


        [MaxLength(200)]
        public string remark { get; set; }

        public int? rdDeptId { get; set; }

        [Column(TypeName = "decimal(18,6)")]
        public decimal amount { get; set; }

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
        /// 凭证号
        /// </summary>
        [MaxLength(100)]
        public string accNumber { get; set; }

        [Column(TypeName = "decimal(18,6)")]
        public decimal totalAmount { get; set; }
    }
}
