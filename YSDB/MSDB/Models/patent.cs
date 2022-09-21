using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    /// <summary>
    /// 专利表
    /// </summary>
    public class patent : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 专利号
        /// </summary>
        [Required, MaxLength(50)]
        public string patentNo { get; set; }
        /// <summary>
        /// 发明名称
        /// </summary>
        [MaxLength(200)]
        public string patentName { get; set; }
        /// <summary>
        /// 申请日期
        /// </summary>
        public DateTime? applyDateTime { get; set; }

        [MaxLength(100)]
        public string mainType { get; set; }
        /// <summary>
        /// 主分类号 
        /// </summary>
        [MaxLength(100)]
        public string mainTypeNo { get; set; }
        /// <summary>
        /// 案件状态
        /// </summary>
        [MaxLength(50)]
        public string caseStatus { get; set; }
        /// <summary>
        /// 分案提交日
        /// </summary>
        public DateTime? caseSubmissionDate { get; set; }
        /// <summary>
        /// 发明人
        /// </summary>
        [MaxLength(200)]
        public string inventor { get; set; }

        /// <summary>
        /// 截至日期
        /// </summary>
        public DateTime? expiryDate { get; set; }

        /// <summary>
        /// 截至缴费金额/应缴金额
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? expiryAmount { get; set; }

        /// <summary>
        /// 关联的客户id
        /// </summary>
        public int? customerId { get; set; }
    }
}
