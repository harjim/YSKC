using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{

    /// <summary>
    /// 研发归集设置
    /// </summary>
    public class p_rdAgg_config : tablebase
    {
        [Key]
        public int id { get; set; }

        /// <summary>
        /// 0 人员， 1设备
        /// </summary>
        public int type { get; set; }

        public int companyId { get; set; }

        /// <summary>
        /// 配置内容
        /// </summary>
        [MaxLength(3000)]
        public string config { get; set; }
        public DateTime month { get; set; }

    }
}
