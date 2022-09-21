using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 研发工时比例
    /// </summary>
    public class p_worktimeRatio : tablebase
    {
        [Key]
        public int id { get; set; }
        public int companyId { get; set; }
        /// <summary>
        /// 0,表示所有项目默认配置
        /// </summary>
        public int projectId { get; set; }
        /// <summary>
        /// 月份
        /// </summary>
        public DateTime month { get; set; }
        /// <summary>
        /// 研究人员
        /// </summary>
        [Column(TypeName ="decimal(5,2)")]
        public decimal research { get; set; }
        /// <summary>
        /// 技术人员
        /// </summary>
        [Column(TypeName = "decimal(5,2)")]
        public decimal technical { get; set; }
        /// <summary>
        /// 辅助人员
        /// </summary>
        [Column(TypeName = "decimal(5,2)")]
        public decimal auxiliary { get; set; }
        /// <summary>
        /// 仪器占工时比
        /// </summary>
        [Column(TypeName = "decimal(5,2)")]
        public decimal laboratory { get; set; }
        /// <summary>
        /// 设备占工时比
        /// </summary>
        [Column(TypeName = "decimal(5,2)")]
        public decimal production { get; set; }

 
    }
}
