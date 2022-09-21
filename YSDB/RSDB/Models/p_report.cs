using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 申报管理表
    /// </summary>
    public class p_report : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 申报年份
        /// </summary>
        public int ryear { get; set; }
        /// <summary>
        /// 申报名称
        /// </summary>
        [Required, MaxLength(100)]
        public string rname { get; set; }

        public int companyId { get; set; }

        [MaxLength(200)]
        public String remark { get; set; }

        /// <summary>
        /// 规划立项数
        /// </summary>
        public int? cnt { get; set; }

        [MaxLength(2000)]
        public string techIntro { get; set; }

        /// <summary>
        /// 规划研发费(万元)
        /// </summary>
        public int? rdFee { get; set; }

        /// <summary>
        /// 人员总数
        /// </summary>
        public int? employeeAmount { get; set; }

        /// <summary>
        /// 营收预测（万元）
        /// fsct=Forecast
        /// </summary>
        public int? revenueFcst { get; set; }

        /// <summary>
        /// 核算类型 0:成本重分类核算研发费,1:冲减主营业务成本列支研发费
        /// </summary>
        public int? accountType { get; set; }
        /// <summary>
        /// 业务预测研发费（万元）
        /// </summary>
        public int? salesRdFee { get; set; }
        /// <summary>
        /// 财务处理方式
        /// </summary>
        public int? finaMode { get; set; }
        /// <summary>
        /// 相关部门【多个逗号分割】
        /// </summary>
        [MaxLength(100)]
        public string deptIds { get; set; }
    }
}
