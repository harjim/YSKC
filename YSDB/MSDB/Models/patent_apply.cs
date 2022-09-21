using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class patent_apply: tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 专利号
        /// </summary>
        [Required, MaxLength(50)]
        public string patentNo { get; set; }

        /// <summary>
        /// 公司名
        /// </summary>
        [Required, MaxLength(50)]
        public string applyName { get; set; }
        /// <summary>
        /// 公司地址
        /// </summary>
        [MaxLength(100)]
        public string address { get; set; }
        /// <summary>
        /// 关联的客户id
        /// </summary>
        public int? customerId { get; set; }
    }
}
