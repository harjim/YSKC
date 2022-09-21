using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 企业成本总计
    /// </summary>
    public class c_cost : tablebase
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
        /// 月份
        /// </summary>
        public int month { get; set; }
        /// <summary>
        /// 费用类型
        /// </summary>
        public int rdType { get; set; }

        /// <summary>
        /// rd费用
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal rdFunds { get; set; }
    }
}
