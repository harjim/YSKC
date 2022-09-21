using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 项目试制表
    /// </summary>
    public class p_trialProd : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 公司id
        /// </summary>
        public int companyId { get; set; }
        /// <summary>
        /// 项目id
        /// </summary>
        public int projectId { get; set; }
        /// <summary>
        /// 试制日期 
        /// </summary>
        public DateTime? trialDate { get; set; }
        /// <summary>
        /// 计划PO量
        /// </summary>
        public int planPO { get; set; }
        /// <summary>
        /// 实际PO量
        /// </summary>
        public int? actualPO { get; set; }
        /// <summary>
        /// 试产机台号
        /// </summary>
        [MaxLength(100)]
        public string pos { get; set; }
        /// <summary>
        /// 试产班组
        /// </summary>
        [MaxLength(100)]
        public string trialGroup { get; set; }
        /// <summary>
        /// 主材消耗
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? mainMaterial { get; set; }
        /// <summary>
        /// 辅材消耗
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? auxMaterial { get; set; }
        /// <summary>
        /// 燃料消耗
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? fuel { get; set; }
        /// <summary>
        /// 动力消耗
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? power { get; set; }
        /// <summary>
        /// 气体
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? gas { get; set; }
        /// <summary>
        /// 预计备品备件(万元)
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? spare { get; set; }

        /// <summary>
        /// 试产量单位
        /// </summary>
        [MaxLength(10)]
        public string unit { get; set; }
        /// <summary>
        /// 地点
        /// </summary>
        [MaxLength(100)]
        public string place { get; set; }
        /// <summary>
        /// 开始时间
        /// </summary>
        public System.TimeSpan? startTime { get; set; }
        /// <summary>
        ///结束时间
        /// </summary>
        public System.TimeSpan? endTime { get; set; }

    }
}
