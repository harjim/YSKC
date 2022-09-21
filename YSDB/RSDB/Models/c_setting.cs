using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 企业成本总计
    /// </summary>
    public class c_setting : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 公司ID 
        /// </summary>
        public int companyId { get; set; }

        ///<summary>
        /// 账期（工资核算区间）
        ///</summary>
        [MaxLength(200)]
        public string accountPeriod { get; set; }

        ///<summary>
        [MaxLength(200)]
        ///</summary>
        public string miniProgram { get; set; }

        ///<summary>
        /// 研发加急扣除比例（年度）[小数]
        ///</summary>
        [MaxLength(500)]
        public string rdRatio { get; set; }

       /// <summary>
        /// 文件编号
        /// </summary>
        [MaxLength(200)]
        public string documentNo { get; set; }
       /// <summary>
        /// 文件编号
        /// </summary>
        [MaxLength(200)]
        public string hourBit { get; set; }
    }
}
