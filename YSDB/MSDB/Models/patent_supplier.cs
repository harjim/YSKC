using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    /// <summary>
    /// 专利供应商
    /// </summary>
    public class patent_supplier : tablebase {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 供应商
        /// </summary>
        [Required, MaxLength(100)]
        public string supplier { get; set; }
        /// <summary>
        /// 户名
        /// </summary>
        [Required, MaxLength(100)]
        public string accountName { get; set; }
        /// <summary>
        /// 帐号
        /// </summary>
        [Required, MaxLength(80)]
        public string accountNumber { get; set; }
        /// <summary>
        /// 联系人
        /// </summary>
        [Required, MaxLength(80)]
        public string linkman { get; set; }
        /// <summary>
        /// 联系电话
        /// </summary>
        [Required, MaxLength(80)]
        public string linkTel { get; set; }

        /// <summary>
        /// 备注
        /// </summary>
        public string remark { get; set; }
    }
}