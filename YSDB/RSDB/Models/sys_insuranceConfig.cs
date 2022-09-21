using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 五险一金配置,按公司默认配置
    /// </summary>
    public class sys_insuranceConfig : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 公司Id
        /// </summary>
        public int companyId { get; set; }
        /// <summary>
        /// 部门id,默认为0，表示所有部门一样
        /// </summary>
        public int deptId { get; set; }
        /// <summary>
        /// 工号，默认为空表示该范围所有员工
        /// </summary>
        [Required, MaxLength(30)]
        public string enumber { get; set; }
        /// <summary>
        /// 开始月份
        /// </summary>
        public DateTime startMonth { get; set; }
        /// <summary>
        /// 结束月份，空值表示还未结束
        /// </summary>
        public DateTime? endMonth { get; set; }

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
    }
}
