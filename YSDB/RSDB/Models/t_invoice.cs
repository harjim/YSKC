using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 发票：子表-->t_invoice_detail
    /// </summary>
    public class t_invoice : tablebase
    {
        [Key]
        public int id { get; set; }

        public int companyId { get; set; }

        ///<summary>
        /// 序号（录入）
        ///</summary>
        public int seq { get; set; }

        ///<summary>
        /// 发票号
        ///</summary>
        [Required, MaxLength(50)]
        public string invoiceNo { get; set; }

        ///<summary>
        /// 含税总额
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal taxAmount { get; set; }

        ///<summary>
        /// 发票日期【开票日期】
        ///</summary>
        public DateTime invoiceDate { get; set; }

        ///<summary>
        /// 发票附件
        ///</summary>
        [MaxLength(500)]
        public string invoicePath { get; set; }

        ///<summary>
        /// 资产转固凭证号(发票记账凭证字号【凭证号】)
        ///</summary>
        [MaxLength(50)]
        public string voucherNo { get; set; }

        ///<summary>
        /// 转固凭证附件
        ///</summary>
        [MaxLength(500)]
        public string voucherPath { get; set; }

        ///<summary>
        /// 转固日期
        ///</summary>
        public DateTime? voucherDate { get; set; }

        ///<summary>
        /// 设备供应商(原 发票开具方)
        ///</summary>
        [Required, MaxLength(50)]
        public string invoiceFrom { get; set; }
    }
}
