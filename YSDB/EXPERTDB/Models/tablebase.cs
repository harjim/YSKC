using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace EXPERTDB.Models
{
    public class tablebase
    {
        /// <summary>
        /// 创建人
        /// </summary>
        public int creatorId { get; set; }
        /// <summary>
        /// 更新
        /// </summary>
        public int lastUpdatorId { get; set; }
        /// <summary>
        /// 创建时间
        /// </summary>
        public DateTime createTime { get; set; }
        public DateTime lastUpdateTime { get; set; }
    }
}
