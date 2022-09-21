using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 高新技术表
    /// </summary>
    public class highTech : tablebase
    {
        [Key]
        public int id { get; set; }
        public int companyId { get; set; }

        /// <summary>
        /// 高新技术领域
        /// </summary>
        [MaxLength(20),Required]
        public string tecIndustry { get; set; }
        
        /// <summary>
        /// 高新技术代码/编号[按年份：2020PS01]
        /// </summary>
        [MaxLength(20),Required]
        public string hcode { get; set; }

        /// <summary>
        /// 高新技术名称
        /// </summary>
        [MaxLength(200),Required]
        public string hname { get; set; }
        /// <summary>
        /// 开始年
        /// </summary>
        public int startYear { get; set; }
        /// <summary>
        /// 结束年 [默认为startYear + 2]
        /// </summary>
        public int endYear { get; set; }
        /// <summary>
        /// 编码number 【hcode = startYear + ‘PS’ + codeNum】
        /// </summary>
        public int codeNum { get; set; }

        /// <summary>
        /// 高品说明
        /// </summary>
        [MaxLength(1000)]
        public string description { get; set; }

    }
}
