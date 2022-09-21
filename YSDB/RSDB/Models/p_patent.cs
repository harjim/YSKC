using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 项目专利表
    /// </summary>
    public class p_patent : tablebase
    { 
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 公司id
        /// </summary>
        public int companyId { get; set; }
        /// <summary>
        /// 项目ID 
        /// </summary>
        public int projectId { get; set; }
        /// <summary>
        /// 专利号
        /// </summary>
        [Required, MaxLength(50)]
        public string patentNo { get; set; }
        /// <summary>
        /// 发明名称
        /// </summary>
        [Required,MaxLength(200)]
        public string patentName { get; set; }
        /// <summary>
        /// 申请日期
        /// </summary>
        public DateTime? applyDate { get; set; }

        /// <summary>
        /// 授权日期
        /// </summary>
        public DateTime? authorizeDate { get; set; }
        /// <summary>
        /// 专利权属
        /// </summary>
        public string ownership { get; set; }
        /// <summary>
        /// 缴费收据报支查找年月
        /// </summary>
        public DateTime? searchDate { get; set; }
    }
}
