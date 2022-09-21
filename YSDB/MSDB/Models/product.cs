using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class product : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 产品名称
        /// </summary>
        [Required, MaxLength(50)]
        public string productName { get; set; }
        /// <summary>
        /// 年份
        /// </summary>
        public int? year { get; set; }
        /// <summary>
        /// 所在地
        /// </summary>
        [MaxLength(100)]
        public string address { get; set; }
        /// <summary>
        /// 1,创新项目
        /// </summary>
        public int productType { get; set; }

        public bool shouldRegister { get; set; }
        /// <summary>
        /// 申报项目类型
        /// </summary>
        public int declareType { get; set; }

    }
}
