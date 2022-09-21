using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 财务状况
    /// </summary>
    public class c_financialCondition : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 公司id
        /// </summary>
        public int companyId { get; set; }
        /// <summary>
        /// 年份
        /// </summary>
        public int year { get; set; }
        /// <summary>
        /// 营业收入（万元）
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal businessIncome { get; set; }
        /// <summary>
        /// 其中：主营业务收入（万元）
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal mainBusinessIncome { get; set; }
        /// <summary>
        /// 净利润（万元）
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal netProfit{ get; set; }
        /// <summary>
        /// 工业总产值（万元）
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal grossOfIndustrial { get; set; }
        /// <summary>
        /// 工业增加值（万元）
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal addedOfIndustrial { get; set; }
        /// <summary>
        /// 总资产（万元）
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal totalAssets { get; set; }
        /// <summary>
        /// 其中：固定资产总额（万元）
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal totalFixedAssets { get; set; }
        /// <summary>
        /// 净资产（万元）
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal netAssets { get; set; }
        /// <summary>
        /// 固定资产投资额（万元）
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal fixedAssetsOfInvestment { get; set; }
        /// <summary>
        /// 总现金流量净额（万元）
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal netTotalCashFlow { get; set; }
        /// <summary>
        /// 经营活动现金流量净额（万元）
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal netCashFlowOfOperating { get; set; }
        /// <summary>
        /// 资产负债率（%）
        /// </summary>
        [Required, Column(TypeName = "decimal(6,2)")]
        public decimal assetLiabilityRatio { get; set; }
        /// <summary>
        /// R&D支出总额（万元）
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal totalExpenditureOfRD { get; set; }
        /// <summary>
        /// 政府借款金额（万元）
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal loanAmountOfGovernment { get; set; }
        /// <summary>
        /// 到期未还的政府借款额（万元）
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal dueLoanOfGovernment { get; set; }
        /// <summary>
        /// 纳税总额（万元）
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal totalTax { get; set; }
        /// <summary>
        /// 企业所得税（万元）
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal corporateIncomeTax { get; set; }
        /// <summary>
        /// 利润总额
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal totalProfit { get; set; }
    }
}
