using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 人员项目考勤表
    /// </summary>
    public class p_rdEmployee : tablebase
    {
        [Key]
        public int id { get; set; }

        public int companyId { get; set; }
        /// <summary>
        /// 项目Id
        /// </summary>
        public int projectId { get; set; }

        /// <summary>
        /// 使用月份
        /// </summary>
        public DateTime month { get; set; }

        /// <summary>
        /// 五险一金
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal rdInsuranceFund { get; set; }

        /// <summary>
        /// 研发工资
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal rdPay { get; set; }
        /// <summary>
        /// 员工编号/标识
        /// </summary>
        [Required, MaxLength(30)]
        public string enumber { get; set; }
        /// <summary>
        /// 研发工时
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal rdHour { get; set; }

        [Column(TypeName = "decimal(18,2)")]
        public decimal rdRatio { get; set; }

        ///<summary>
        ///研发考勤工时
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal attendanceHour { get; set; }

        ///<summary>
        /// 研发考勤修改
        ///</summary>
        public bool attendanceEdit { get; set; }
    }
}
