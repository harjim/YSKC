using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace MSDB.Models
{
    public class quality_score : tablebase
    {
        [Key]
        public int id { get; set; }

        public int companyId { get; set; }

        public int? rsProjectId { get; set; }

        public int year { get; set; }

        public DateTime month { get; set; }

        ///<summary>
        ///打分次数 超过三次（包含第三次），所有得分为0
        ///</summary>
        public int scoreCount { get; set; }

        ///<summary>
        /// 工程师ID
        ///</summary>
        public int engineerId { get; set; }

        ///<summary>
        /// 当次总分
        ///</summary>
        [Column(TypeName = "decimal(5,2)")]
        public decimal totalScore { get; set; }

        ///<summary>
        /// 平均分(当scoreCount大于等于3时，该值为0)
        ///</summary>
        [Column(TypeName = "decimal(5,2)")]
        public decimal avgScore { get; set; }

        ///<summary>
        /// 权重
        ///</summary>
        [Column(TypeName = "decimal(5,3)")]
        public decimal weight { get; set; }
        ///<summary>
        /// 总权重
        ///</summary>
        [Column(TypeName = "decimal(5,3)")]
        public decimal totalWeight { get; set; }

        ///<summary>
        /// 通过情况
        ///</summary>
        [Column(TypeName = "decimal(5,2)")]
        public decimal passRate { get; set; }

        ///<summary>
        /// 类型：0 立项报告
        ///</summary>
        public int type { get; set; }

        public bool isFinal { get; set; }

        public int? monthsId { get; set; }

        ///<summary>
        ///提交时间
        ///</summary>
        public DateTime submitTime { get; set; }

        ///<summary>
        ///审核时间
        ///</summary>
        public DateTime auditTime { get; set; }
    }
}
