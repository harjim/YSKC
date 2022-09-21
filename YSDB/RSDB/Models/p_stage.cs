using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 项目阶段
    /// </summary>
    public class p_stage : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 项目Id
        /// </summary>
        public int projectId { get; set; }
        /// <summary>
        /// 阶段名
        /// </summary>
        [MaxLength(50)]
        public string stageName { get; set; }
        /// <summary>
        ///开始日期
        /// </summary>
        public DateTime? beginDate { get; set; }
        /// <summary>
        /// 结束日期
        /// </summary>
        public DateTime? endDate { get; set; }

        /// <summary>
        /// 备注
        /// </summary>
        [MaxLength(200)]
        public string remark { get; set; }

        public int companyId { get; set; }
        /// <summary>
        /// 工作内容
        /// </summary>
        [MaxLength(200)]
        public string workDesc { get; set; }
        /// <summary>
        /// 阶段类型
        /// </summary>
        [Required,MaxLength(10)]
        public string stageKey { get; set; }
    }
}
