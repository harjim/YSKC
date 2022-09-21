using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 市政府扶持概况
    /// </summary>
    public class c_support : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 公司id
        /// </summary>
        public int companyId { get; set; }
        /// <summary>
        /// 项目名称
        /// </summary>
        [Required, MaxLength(200)]
        public string projectName { get; set; }
        /// <summary>
        /// 开始时间
        /// </summary> 
        public DateTime? startTime { get; set; }
        /// <summary>
        /// 结束时间
        /// </summary> 
        public DateTime? endTime { get; set; }
 
        /// <summary>
        /// 扶持时间
        /// </summary>
        [MaxLength(30)]
        public string supportTime { get; set; }
        /// <summary>
        /// 扶持部门
        /// </summary>
        [Required, MaxLength(100)]
        public string supportDeptName { get; set; }
        /// <summary>
        /// 扶持金额（万元）
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal supportAmount { get; set; }
        /// <summary>
        /// 验收时间
        /// </summary>
        [Required]
        public DateTime checkTime { get; set; }
        /// <summary>
        /// 验收结果
        /// </summary>
        [MaxLength(100)]
        public string checkResult { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [MaxLength(200)]
        public string remark { get; set; }
        /// <summary>
        /// 扶持年份
        /// </summary>
        public int syear { get; set; }
        /// <summary>
        /// 负责人
        /// </summary>
        [MaxLength(30)]
        public string master { get; set; }
        ///<summary>
        /// 下达字号
        /// </summary>
        [MaxLength(50)]
        public string issuceNum { get; set; }
    }
}
