using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class tablebase
    {
        /// <summary>
        /// 创建人,申报端
        /// </summary>
        public int creatorId { get; set; }
        /// <summary>
        /// 申报端
        /// </summary>
        public int lastUpdatorId { get; set; }
        /// <summary>
        /// 创建时间
        /// </summary>
        public DateTime createTime { get; set; }
        public DateTime lastUpdateTime { get; set; }

    }
}
