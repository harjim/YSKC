using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    ///  合同明细【合同子表】
    /// </summary>
    public class t_contract_detail : tablebase
    {
        [Key]
        public int id { get; set; }

        public int companyId { get; set; }

        ///<summary>
        /// 投资id
        ///</summary>
        public int? investmentId { get; set; }

        public int contractId { get; set; }

        ///<summary>
        /// 设备名称
        ///</summary>
        [Required, MaxLength(200)]
        public string ename { get; set; }

        ///<summary>
        /// 设备型号
        ///</summary>
        [Required, MaxLength(100)]
        public string emodal { get; set; }

        ///<summary>
        /// 数量
        ///</summary>
        public int quantity { get; set; }

        /// <summary>
        /// 金额(含税)（元）
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal amount { get; set; }

        ///<summary>
        /// 是否关联方购置
        ///</summary>
        public bool partPurchase { get; set; }

        ///<summary>
        /// 是否二手
        ///</summary>
        public bool secondHand { get; set; }

        ///<summary>
        /// 是否经销商购置
        ///</summary>
        public bool traderPurchase { get; set; }

    }
}
