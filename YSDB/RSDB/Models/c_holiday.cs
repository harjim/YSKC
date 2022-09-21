using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 企业假期
    /// </summary>
    public class c_holiday : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 公司ID 
        /// </summary>
        public int companyId { get; set; }
        /// <summary>
        /// 月份
        /// </summary>
        public DateTime month { get; set; }
        /// <summary>
        /// 假期日期
        /// </summary>
        [MaxLength(80)]
        public string holidays { get; set; }
    }
}
