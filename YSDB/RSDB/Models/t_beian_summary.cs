using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 备案汇总信息
    /// 所有金额单位为万元
    /// </summary>
    public class t_beian_summary : tablebase
    {
        [Key]
        public int id { get; set; }

        public int companyId { get; set; }

        public int beianId { get; set; }

        ///<summary>
        /// 备案金额(备案总投资)
        /// sum(equipment + initWorkCapital + construction)
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? totalAmount { get; set; }

        ///<summary>
        /// 备案金额(含税)(备案总投资(含税))
        /// sum(equipmentTax + initWorkCapitalTax + constructionTax)
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? totalAmountTax { get; set; }

        ///<summary>
        /// 固定资产投资(不含铺底流动资金)
        ///  sum(equipment + construction)
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? amount { get; set; }

        ///<summary>
        /// 固定资产投资(含税)(不含铺底流动资金) 
        ///  sum(equipmentTax + constructionTax)
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? amountTax { get; set; }

        ///<summary>
        /// 设备费
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? equipment { get; set; }

        ///<summary>
        /// 设备费(含税)
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? equipmentTax { get; set; }

        ///<summary>
        /// 铺底流动资金
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? initWorkCapital { get; set; }

        ///<summary>
        /// 铺底流动资金(含税)
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? initWorkCapitalTax { get; set; }

        ///<summary>
        /// 建设费
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? construction { get; set; }

        ///<summary>
        /// 建设费(含税)
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? constructionTax { get; set; }

        // ///<summary>
        // /// 已完成设备费
        // ///</summary>
        // [Column(TypeName = "decimal(18,4)")]
        // public decimal? equipmentCost { get; set; }

        // ///<summary>
        // /// 已完成建设费
        // ///</summary>
        // [Column(TypeName = "decimal(18,4)")]
        // public decimal? constructionCost { get; set; }

        // ///<summary>
        // /// 已完成铺底流动资金
        // ///</summary>
        // [Column(TypeName = "decimal(18,4)")]
        // public decimal? initWorkCapitalCost { get; set; }

        // ///<summary>
        // /// 完成情况
        // ///</summary>
        // [Column(TypeName = "decimal(18,2)")]
        // public decimal? completeRate { get; set; }
    }
}
