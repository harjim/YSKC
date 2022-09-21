using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class patent_plan_info : tablebase
    {
        [Key]
        public int id { get; set; }
        
        ///<summary>
        ///专利申请id
        ///</summary>
        public int patentPlanId { get; set; }

        ///<summary>
        ///提交国知局日期
        ///</summary>
        public DateTime? submittedDate { get; set; }
        ///<summary>
        ///受理通知日期
        ///</summary>
        public DateTime? acceptNoticeDate { get; set; }
        ///<summary>
        ///受理缴费日期
        ///</summary>
        public DateTime? acceptFeeDate { get; set; }

        ///<summary>
        ///受理费用
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? acceptFee { get; set; }
        ///<summary>
        ///授权日期(保存该日期时，需要同时更改rsdb.patent.authDate)
        ///</summary>
        public DateTime? authDate { get; set; }
        ///<summary>
        ///授权缴费日期
        ///</summary>
        public DateTime? authFeeDate { get; set; }

        ///<suumary>
        ///授权费用
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? authFee { get; set; }
        ///<summary>
        ///下证日期
        ///</summary>
        public DateTime? issueDate { get; set; }

    }
}