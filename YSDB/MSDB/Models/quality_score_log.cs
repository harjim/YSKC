using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace MSDB.Models
{
    public class quality_score_log : tablebase
    {
        [Key]
        public int id { get; set; }

        public int qualityId { get; set; } 

        ///<summary>
        ///打分次数 超过三次（包含第三次），所有得分为0
        ///</summary>
        public int scoreCount { get; set; }

        ///<summary>
        /// 得分详情
        ///</summary>
        [MaxLength(500)]
        public string scores { get; set; }

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
        ///提交时间
        ///</summary>
        public DateTime submitTime { get; set; }

        ///<summary>
        ///审核时间
        ///</summary>
        public DateTime auditTime { get; set; }
        
        ///<summary>
        /// 工程师ID
        ///</summary>
        public int engineerId { get; set; }
        ///<summary>
        ///评分月份
        ///</summary>
        public DateTime month { get; set; }
        ///<summary>
        ///通过
        ///</summary>
        public bool passed { get; set; }
    }
}
