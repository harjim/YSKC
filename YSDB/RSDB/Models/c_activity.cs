using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 企业研究开发活动及相关情况
    /// </summary>
    public class c_activity : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 公司ID 
        /// </summary>
        public int companyId { get; set; }
        /// <summary>
        /// 年份
        /// </summary>
        public int year { get; set; }
        /// <summary>
        ///
        /// </summary>
        [Required, MaxLength(50)]
        public string pKey { get; set; }
        /// <summary>
        /// 值
        /// </summary>
        [Required, MaxLength(50)]
        public string pValue { get; set; }
    }
}
