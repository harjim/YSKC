using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 员工薪资
    /// </summary>
    public class d_salary : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 员工编号/标识
        /// </summary>
        [Required, MaxLength(30)]
        public string enumber { get; set; }
        /// <summary>
        /// 姓名
        /// </summary>
        [Required, MaxLength(20)]
        public string ename { get; set; }
        /// <summary>
        /// 工资月份
        /// </summary>
        public DateTime month { get; set; }
        /// <summary>
        /// 工作天数(考虑会有0.5天的数据)
        /// </summary>
        public decimal workDays { get; set; }
        /// <summary>
        /// 工时,每个月工作的小时数。
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal workHours { get; set; }
        /// <summary>
        /// 应发工资
        /// </summary>
        public decimal pay { get; set; }
        /// <summary>
        /// 五险一金，公司付的部分
        /// </summary>
        public decimal insuranceFund { get; set; }
        /// <summary>
        /// 养老
        /// </summary>
        public decimal endowment { get; set; }
        /// <summary>
        /// 医疗
        /// </summary>
        public decimal medical { get; set; }
        /// <summary>
        /// 失业
        /// </summary>
        public decimal unemployment { get; set; }
        /// <summary>
        /// 工伤
        /// </summary>
        public decimal injury { get; set; }
        /// <summary>
        /// 生育
        /// </summary>
        public decimal maternity { get; set; }
        /// <summary>
        /// 公积金
        /// </summary>
        public decimal house { get; set; }

        /// <summary>
        /// 养老,公司上交部分
        /// </summary>
        public decimal endowmentOfCom { get; set; }
        /// <summary>
        /// 医疗,公司上交部分
        /// </summary>
        public decimal medicalOfCom { get; set; }
        /// <summary>
        /// 失业,公司上交部分
        /// </summary>
        public decimal unemploymentOfCom { get; set; }
        /// <summary>
        /// 工伤,公司上交部分
        /// </summary>
        public decimal injuryOfCom { get; set; }
        /// <summary>
        /// 生育,公司上交部分
        /// </summary>
        public decimal maternityOfCom { get; set; }
        /// <summary>
        /// 公积金,公司上交部分
        /// </summary>
        public decimal houseOfCom { get; set; }

        public int companyId { get; set; }

        /// <summary>
        /// 备注
        /// </summary>
        [MaxLength(200)]
        public String remark { get; set; }
        /// <summary>
        /// 工作制小时
        /// </summary>
        public int dayHours { get; set; }

        /// <summary>
        /// 关联科目id
        /// </summary>
        public int accountTitleId { get; set; }

        /// <summary>
        /// 薪资明细
        /// </summary>
        [MaxLength(2000)]
        public string payDetail { get; set; }


        /// <summary>
        /// 五险一金明细
        /// </summary>
        [MaxLength(2000)]
        public string insuranceDetail { get; set; }

        /// <summary>
        /// 部门
        /// </summary>
        [MaxLength(100)]
        public string deptName { get; set; }
    }
}
