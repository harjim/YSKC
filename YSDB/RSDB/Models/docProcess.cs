using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 过程管理
    /// </summary>
    public class docProcess
    {
        [Key]
        public int id { get; set; }

        /// <summary>
        /// 过程名称
        /// </summary>
        [Required,MaxLength(30)]
        public string processName { get; set; }

        /// <summary>
        /// 类型，暂时默认为0，界面暂时不显示
        /// </summary>
        public int type { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [MaxLength(100)]
        public string remark { get; set; }

        /// <summary>
        /// 创建人
        /// </summary>
        public int msCreatorId { get; set; }
        /// <summary>
        /// 创建时间
        /// </summary>
        public DateTime createTime { get; set; }
        /// <summary>
        /// 最后操作人id
        /// </summary>
        public int lastMsUpdatorId { get; set; }
        /// <summary>
        /// 最后更新时间
        /// </summary>
        public DateTime lastUpdateTime { get; set; }

    }
}
