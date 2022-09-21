using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace RSDB.Models
{

    ///<summary>
    ///优惠明细账填写费用项
    ///</summary>
    public class p_year_fee : tablebase
    {
        [Key]
        public int id { get; set; }

        public int companyId { get; set; }

        public int year { get; set; }
        /// <summary>
        /// 46.减：特殊收入部分
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? k46 { get; set; }
        /// <summary>
        /// 48.减：当年销售研发活动直接形成产品（包括组成部分）对应的材料部分
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? k48 { get; set; }
        /// <summary>
        /// 49.减：以前年度销售研发活动直接形成产品（包括组成部分）对应材料部分结转金额
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? k49 { get; set; }
        public decimal rdRatio { get; set; }
    }
}