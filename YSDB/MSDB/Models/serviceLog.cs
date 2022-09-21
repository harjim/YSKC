using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    /// <summary>
    /// 客户服务记录
    /// </summary>
    public class serviceLog : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 客户id
        /// </summary>
        public int customerId { get; set; }
        /// <summary>
        /// 服务方式0现场对接1电话沟通2其他
        /// </summary>
        public int serviceType { get; set; }
        /// <summary>
        /// 服务总工时
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? totalTime { get; set; }
        /// <summary>
        /// 服务总人数
        /// </summary>
        public int totalStaff { get; set; }
        /// <summary>
        /// 上次问题解决情况
        /// </summary>
        [MaxLength(800)]
        public String lastProblem { get; set; }
        /// <summary>
        /// 本次遇到问题及解决方案
        /// </summary>
        [MaxLength(800)]
        public String nowProblem { get; set; }
        /// <summary>
        ///客户建议
        /// </summary>
        [MaxLength(800)]
        public String customerAdvice { get; set; }
        /// <summary>
        ///客户主要参与人员
        /// </summary>
        [MaxLength(100)]
        public String customerParticipan { get; set; }
        /// <summary>
        ///本次服务具体的车间/部门/工艺段
        /// </summary>
        [MaxLength(100)]
        public String serviceDept { get; set; }
        /// <summary>
        /// 是否提交0未提交1提交
        /// </summary>
        public int status { get; set; }
        /// <summary>
        /// 服务开始时间 
        /// </summary>
        public DateTime startDate { get; set; }
        /// <summary>
        /// 服务结束时间
        /// </summary>
        public DateTime endDate { get; set; }

        /// <summary>
        ///审核意见
        /// </summary>
        [MaxLength(500)]
        public String auditOpinion { get; set; }
        /// <summary>
        ///上传地址
        /// </summary>
        [MaxLength(500)]
        public String filePath { get; set; }
        /// <summary>
        /// 提交时间
        /// </summary>
        public DateTime? submitDate { get; set; }
        ///<summary>
        ///服务天数
        ///</summary>
        public int days { get; set; }

    }
}
