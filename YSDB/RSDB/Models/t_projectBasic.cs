using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 项目基本情况
    /// </summary>
    public class t_projectBasic:tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 项目id，t_project
        /// </summary>
        public int projectId { get; set; }
        /// <summary>
        /// 公司id
        /// </summary>
        public int companyId { get; set; }
        /// <summary>
        /// 项目所属行业和代码
        /// </summary>
        [Required, MaxLength(50)]
        public string industryCode { get; set; }
        /// <summary>
        /// 项目备案号
        /// </summary>
        [Required, MaxLength(50)]
        public string recordNumber { get; set; }
        /// <summary>
        /// 项目属于
        /// </summary>
        [Required, MaxLength(30)]
        public string projectType { get; set; }
        /// <summary>
        /// 建设性质
        /// </summary>
        public int conssRuctionType { get; set; }
        /// <summary>
        /// 节水改造技术领域
        /// </summary>
        [Required, MaxLength(30)]
        public string technicalField { get; set; }
        /// <summary>
        /// 无涉及节水改造，请说明理由
        /// </summary>
        [MaxLength(200)]
        public string notInvolveRemark { get; set; }
        /// <summary>
        /// 项目建设主要内容
        /// </summary>
        [Required, Column(TypeName = "text")]
        public string mainContents { get; set; }
    }
}
