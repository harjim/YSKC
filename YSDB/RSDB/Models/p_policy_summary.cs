using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 政策及要求情况汇总表
    /// </summary>
    public class p_policy_summary : tablebase
    {
         [Key]
        public int id { get; set; }


          /// <summary>
        /// 项目实施依据法规
        /// </summary>
        [MaxLength(1000)]
        public string statute { get; set; }

        /// <summary>
        /// 备案资料
        /// </summary>
        [MaxLength(1000)]
        public string filingMaterial { get; set; }

         /// <summary>
        /// 加计扣除流程
        /// </summary>
        [MaxLength(1000)]
        public string additionalDeduction { get; set; }
    }
}