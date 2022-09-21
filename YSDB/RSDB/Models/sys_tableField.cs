using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class sys_tableField : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 公司id
        /// </summary>
        public int companyId { get; set; }
        /// <summary>
        /// 表格标志
        /// </summary>
        [Required, MaxLength(100)]
        public string tableId { get; set; }
        /// <summary>
        /// 表格头部
        /// </summary>
        [Required, MaxLength(4000)]
        public string fieldTitle { get; set; }

    }
}
