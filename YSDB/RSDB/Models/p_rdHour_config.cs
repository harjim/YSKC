using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{

    /// <summary>
    /// 研发工时设置
    /// </summary>
    public class p_rdHour_config : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 项目id
        /// </summary>
        public int projectId { get; set; }

        /// <summary>
        /// 0 人员， 1设备
        /// </summary>
        public int type { get; set; }

        public int companyId { get; set; }

        public DateTime month { get; set; }

        /// <summary>
        /// 配置内容
        /// </summary>
        [MaxLength(2500)]
        public string config { get; set; }

    }
}
