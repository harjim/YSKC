using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    /// <summary>
    ///合同追溯表
    /// </summary>
    public class contractTraceability : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 合同编号
        /// </summary>
        [Required, MaxLength(30)]
        public string contractNumber { get; set; }

        public int? customerId { get; set; }

        [MaxLength(200)]
        public string customerName { get; set; }
        /// <summary>
        /// 封面
        /// </summary>
        [Required, MaxLength(200)]
        public string thecover { get; set; }
        /// <summary>
        /// 二维码图片地址
        /// </summary>
        [Required, MaxLength(100)]
        public string qrcode { get; set; }


    }
}
