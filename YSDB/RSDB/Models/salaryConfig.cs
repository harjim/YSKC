using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 薪资，五险一金，设备，人员列配置
    /// </summary>
    public class salaryConfig : tablebase
    {
        [Key]
        public int id { get; set; }

        public int companyId { get; set; }

        /// <summary>
        /// 配置（json）
        /// </summary>
        [MaxLength(2000)]
        public string config { get; set; }

        /// <summary>
        /// 配置个数，不可逆转，正增长
        /// </summary>
        public int number { get; set; }

        [MaxLength(200)]
        public string remark { get; set; }

        ///<summary>
        /// 配置类型： 0 薪资，1 五险一金
        ///</summary>
        public int type { get; set; }

    }
}
