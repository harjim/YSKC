using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 投资付款
    /// </summary>
    public class t_payment : tablebase
    {
        [Key]
        public int id { get; set; }

        public int companyId { get; set; }
        ///<summary>
        /// 投资id
        ///</summary>
        public int? investmentId { get; set; }

        ///<summary>
        /// 序号（录入）
        ///</summary>
        public int seq { get; set; }

        ///<summary>
        ///付款记账凭证字号
        ///</summary>
        [MaxLength(50)]
        public string voucherNo { get; set; }

        ///<summary>
        ///记账日期
        ///</summary>
        public DateTime voucherDate { get; set; }

        ///<summary>
        ///付款方式（转账形式）
        /// 0：银行转账，1：承兑汇票，2：现金
        /// payType=1，acceptDate不可为空
        ///</summary>
        public int payType { get; set; }

        ///<summary>
        /// 承兑汇票到期日期
        ///</summary>
        public DateTime? acceptDate { get; set; }

        ///<summary>
        /// 税率
        ///</summary>
        [Column(TypeName = "decimal(5,4)")]
        public decimal taxRate { get; set; }

        ///<summary>
        /// 金额(不含税)
        /// amount = amountTax / (1 + taxRate)
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal amount { get; set; }

        ///<summary>
        /// 金额(含税)
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal amountTax { get; set; }

        ///<summary>
        /// 付款日期
        ///</summary>
        public DateTime payDate { get; set; }

        ///<summary>
        /// 收款单位
        ///</summary>
        [Required, MaxLength(50)]
        public string payee { get; set; }

        ///<summary>
        /// 付款阶段
        /// 0：预付款，1：中期款，2：验收款，3：质保金
        ///</summary>
        public int payStage { get; set; }

        ///<summary>
        /// 付款比例
        ///</summary>
        [Column(TypeName = "decimal(5,2)")]
        public decimal payRate { get; set; }

        ///<summary>
        /// 付款凭证附件
        ///</summary>
        [MaxLength(200)]
        public string voucherPath { get; set; }

        ///<summary>
        /// 银行水单
        ///</summary>
        [MaxLength(200)]
        public string bankReceiptPath { get; set; }
    }
}
