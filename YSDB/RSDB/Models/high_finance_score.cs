using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class high_finance_score : tablebase
    {
        [Key]
        public int id { get; set; }
        public int companyId { get; set; }
        public int year { get; set; }
        ///<summary>
        /// 第一年销售额(万元)
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? salesYear1 { get; set; }
        ///<summary>
        /// 第二年销售额(万元)
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? salesYear2 { get; set; }
        ///<summary>
        /// 第三年销售额(万元)
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? salesYear3 { get; set; }
        ///<summary>
        /// 近三年销售收入(万元)
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? totalSales { get; set; }
        ///<summary>
        /// 第一年净资产(万元)
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? NAVYear1 { get; set; }
        ///<summary>
        /// 第二年净资产(万元)
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? NAVYear2 { get; set; }
        ///<summary>
        /// 第三年净资产(万元)
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? NAVYear3 { get; set; }

        ///<summary>
        /// 近一年企业总收入(万元)
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? income { get; set; }
        ///<summary>
        /// 净资产增长率得分
        ///</summary>
        public int? NAVScore { get; set; }

        ///<summary>
        /// 销售收入增长率得分
        ///</summary>
        public int? salesScore { get; set; }

    }
}