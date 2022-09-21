using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class t_product_stage
    {
        [Key]
        public int id { get; set; }

        /// <summary>
        /// 阶段key
        /// <summary>
        [MaxLength(10), Required]
        public string stageKey { get; set; }
        /// <summary>
        /// 申报方向id
        /// </summary>
        public int directionId { get; set; }

        public DateTime expiryDate { get; set; }

        /// <summary>
        /// 创建人
        /// </summary>
        public int creatorId { get; set; }
        /// <summary>
        /// 更新人
        /// </summary>
        public int lastUpdatorId { get; set; }
        /// <summary>
        /// 创建时间
        /// </summary>
        public DateTime createTime { get; set; }
        public DateTime lastUpdateTime { get; set; }
        public int seq { get; set; }
    }
}