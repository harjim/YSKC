using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class project_basic : tablebase
    {
        [Key]
        public int id { get; set; }


        public int projectId { get; set; }
        ///<summary>
        /// 是否实施标准化
        ///</summary>
        public int? isImplemented { get; set; }

        ///<summary>
        /// 收入
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? income { get; set; }

        ///<summary>
        /// 所得税
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? incomeTax { get; set; }

        ///<summary>
        /// 人数
        ///</summary>
        public int? totalStaff { get; set; }

        ///<summary>
        /// 研发费
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? researchFee { get; set; }
        ///<summary>
        /// 减免所得税
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? taxRefiefs { get; set; }
        ///<summary>
        /// 技术人员情况
        /// </summary>
        [MaxLength(200)]
        public string techStaff { get; set; }
        ///<summary>
        /// 技术人员情况
        /// </summary>
        [MaxLength(200)]
        public string financeStaff { get; set; }
        ///<summary>
        /// 高层情况
        /// </summary>
        [MaxLength(200)]
        public string manager { get; set; }
        ///<summary>
        /// 启动时间
        /// </summary>
        public DateTime? startTime { get; set; }
        ///<summary>
        /// 高新申请状态
        /// </summary>
        public int? applyStatus { get; set; }
        /// <summary>
        /// 是否支付专利费
        /// </summary>
        public bool? hasPayPatent { get; set; }
        ///<summary>
        /// 其他要求
        /// </summary>
        [MaxLength(200)]
        public string other { get; set; }

    }
}