using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class t_product_stage_list
    {
        [Key]
        public int id { get; set; }

        /// <summary>
        /// 阶段id
        /// <summary>
        public int stageId { get; set; }

        /// <summary>
        /// 申报方向id
        /// </summary>
        public int directionId { get; set; }

        /// <summary>
        /// 资料名
        /// </summary>
        [MaxLength(100), Required]
        public string itemName { get; set; }

        /// <summary>
        /// 资料类型 0: 交付，1： 对接 
        /// </summary>
        public int itemType { get; set; }

        /// <summary>
        /// 文件类型
        /// </summary>
        [MaxLength(50)]
        public string pattern { get; set; }

        /// <summary>
        /// 备注
        /// </summary>
        [MaxLength(200)]
        public string remark { get; set; }

        public bool required { get; set; }


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