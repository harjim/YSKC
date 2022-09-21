using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    ///<summary>
    /// 试制计划表
    ///</summary>
    public class p_yield_config : tablebase
    {

        [Key]
        public int id { get; set; }
        [MaxLength(100), Required]
        public string deptName { get; set; }

        public int projectId { get; set; }

        public DateTime month { get; set; }

        ///<summary>
        /// 总产量-->总量
        ///</summary>
        [Column(TypeName = "decimal(18,6)")]
        public decimal totalYield { get; set; }

        ///<summary>
        /// 试制量
        ///</summary>
        [Column(TypeName = "decimal(18,6)")]
        public decimal rdYield { get; set; }

        public int companyId { get; set; }

        ///<summary>
        /// 计划产量-->试制量
        ///</summary>
        [Column(TypeName = "decimal(18,6)")]
        public decimal? planYield { get; set; }

        [MaxLength(10)]
        public string unit { get; set; }
        ///<summary>
        /// 试制品名
        ///</summary> 
        [MaxLength(100)]
        public string trialProduct { get; set; }
        ///<summary>
        /// 试制日期
        ///</summary> 
        public DateTime? trialDate { get; set; }

        /// <summary>
        /// 开始时间
        /// </summary>
        public System.TimeSpan? startTime { get; set; }
        /// <summary>
        ///结束时间
        /// </summary>
        public System.TimeSpan? endTime { get; set; }

        ///<summary>
        /// 来源：0：导入，添加，1：生成
        ///</summary>
        public int source { get; set; }
        public bool selected { get; set; }

        ///<summary>
        /// 运行工时
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal totalHour { get; set; }
        ///<summary>
        /// 研发工时
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal rdHour { get; set; }
        ///<summary>
        /// 试制工时
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal trialHour { get; set; }
        ///<summary>
        /// 检验工时
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? testHour { get; set; }
        /// <summary>
        /// 检验开始时间
        /// </summary>
        public System.TimeSpan? testStartTime { get; set; }
        /// <summary>
        ///检验结束时间
        /// </summary>
        public System.TimeSpan? testEndTime { get; set; }



    }
}