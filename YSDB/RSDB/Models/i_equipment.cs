using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 立项设备清单
    /// </summary>
    public class i_equipment : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 项目Id
        /// </summary>
        public int projectId { get; set; }
        /// <summary>
        /// 资产编码
        /// </summary>
        [Required, MaxLength(100)]
        public string ecode { get; set; }

        public int companyId { get; set; }
        [MaxLength(100)]
        public string effect { get; set; }
        /// <summary>
        /// 进入时间
        /// </summary>
        public DateTime? entryDate { get; set; }
         /// <summary>
        /// 年份
        /// </summary>
        public short year { get; set; }

    }
}
