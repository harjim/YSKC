using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;
namespace RSDB.Models
{
    
    /// <summary>
    /// 银行水单(已废弃)
    /// </summary>
    public class t_bankReceipt : tablebase
    {
        [Key]
        public int id { get; set; }
        public int companyId { get; set; }
        public bool transfer { get; set; }
        ///<summary>
        /// 转账时间
        ///</summary>
        public DateTime? transferDate { get; set; }
        ///<summary>
        /// 金额
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal amount { get; set; }
        ///<summary>
        /// 收款单位
        ///</summary>
        [Required, MaxLength(50)]
        public string payee { get; set; }
        ///<summary>
        /// 银行转账记账凭证字号
        ///</summary>
        [MaxLength(50)]
        public string voucherNo { get; set; }
        ///<summary>
        /// 付款时间
        ///</summary>
        public DateTime? payDate { get; set; }
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