using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 项目情况汇总
    /// </summary>
    public class p_situation_summary : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 公司
        /// </summary>
        public int companyId { get; set; }
        /// <summary>
        /// 年份
        /// </summary>
        public int year { get; set; }
        /// <summary>
        /// 年度回顾总述
        /// </summary>
        [MaxLength(1000)]
        public string annualReview { get; set; }
        /// <summary>
        ///团队努力情况
        /// </summary>
        [MaxLength(1000)]
        public string teamEffort { get; set; }
        /// <summary>
        /// 进度计划回顾
        /// </summary>
        [MaxLength(1000)]
        public string scheduleReview { get; set; }

        /// <summary>
        /// 价值实现情况
        /// </summary>
        [MaxLength(1000)]
        public string valueRealization { get; set; }

        /// <summary>
        /// 后续需加强点
        /// </summary>
        [MaxLength(1000)]
        public string improvementPoints { get; set; }
          /// <summary>
        /// 研发参与部门
        /// </summary>
        [MaxLength(1000)]
        public string rdDepts{ get; set; }
        /// <summary>
        /// 研发内容
        /// </summary>
        [MaxLength(1000)]
        public string rdContent { get; set; }
        /// <summary>
        /// 取得的成效
        /// </summary>
        [MaxLength(1000)]
        public string achievements { get; set; }
    }
}
