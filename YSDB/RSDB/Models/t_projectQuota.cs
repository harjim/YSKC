using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 技改项目指标
    /// </summary>
    public class t_projectQuota : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 项目id，t_project
        /// </summary>
        public int projectId { get; set; }
        /// <summary>
        /// 公司id
        /// </summary>
        public int companyId { get; set; }
        /// <summary>
        /// 工业投资
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal investment{ get; set; }
        /// <summary>
        /// 工业技术改造投资
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal transform { get; set; }
        /// <summary>
        /// 水改造投入
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal water { get; set; }

        /// <summary>
        /// 工业投资(计划)
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal investmentOfPlan { get; set; }
        /// <summary>
        /// 工业技术改造投资(计划)
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal transformOfPlan { get; set; }
        /// <summary>
        /// 水改造投入(计划)
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal waterOfPlan { get; set; }
        /// <summary>
        /// 企业工业机器人保有数量
        /// </summary>
        public int robotsCount { get; set; }
        /// <summary>
        /// 新增工业机器人总数
        /// </summary>
        public int robotsOfPlan { get; set; }
        /// <summary>
        /// 使用的国外工业机器人数量
        /// </summary>
        public int robotsOfAbroad { get; set; }
        /// <summary>
        /// 国产（不含粤产） 工业机器人数量
        /// </summary>
        public int robotsOfDomestic { get; set; }
        /// <summary>
        /// 粤产工业机器人数量
        /// </summary>
        public int robotsOfGd { get; set; }
    }
}
