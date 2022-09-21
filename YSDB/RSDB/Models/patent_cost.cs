using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 专利费用
    /// </summary>
    public class patent_cost : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 专利号
        /// </summary>
        [Required, MaxLength(50)]
        public string patentNo { get; set; }
        /// <summary>
        /// 费用类型
        /// </summary>
        [Required, MaxLength(200)]
        public string costType { get; set; }
        /// <summary>
        /// 缴费期限
        /// </summary>
        public DateTime? limitDate { get; set; }
        /// <summary>
        /// 缴费金额
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal amount { get; set; }
        /// <summary>
        /// 是否缴费
        /// </summary>
        public bool isPay { get; set; }
        /// <summary>
        /// 缴费日期
        /// </summary>
        public DateTime? payDateTime { get; set; }
        /// <summary>
        /// 是否提醒
        /// </summary>
        public bool isRemind { get; set; }
        /// <summary>
        /// 提醒日期
        /// </summary>
        public DateTime? remindDateTime { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [MaxLength(200)]
        public string remark { get; set; }
        /// <summary>
        /// 收据号
        /// </summary>
        [MaxLength(50)]
        public String receiptNo { get; set; }

        /// <summary>
        /// isPay的其他状态
        /// </summary>
        [MaxLength(20)]
        public String status { get; set; }
        /// <summary>
        /// 付款人
        /// </summary>
        [MaxLength(100)]
        public String payer { get; set; }
    }
}

