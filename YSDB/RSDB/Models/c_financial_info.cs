using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 财务状况
    /// </summary>
    public class c_financial_info : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 公司id
        /// </summary>
        public int companyId { get; set; }
        /// <summary>
        /// 年份
        /// </summary>
        public int year { get; set; }
        [Column(TypeName = "decimal(18,2)")]
        public decimal value { get; set; }
        [MaxLength(50), Required]
        public string key { get; set; }
    }
}