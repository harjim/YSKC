using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class t_direction
    {
        [Key]
        public int id { get; set; }
        ///<summary>
        /// 方向
        /// <summary>
        [MaxLength(50), Required]
        public string direction { get; set; }
        
        ///<summary>
        /// 主方向
        /// <summary>
        [MaxLength(50)]
        public string mainDirection { get; set; }

        public DateTime expiryDate { get; set; }

        /// <summary>
        /// 申报项目id
        /// <summary>
        public int productId { get; set; }

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


        ///<summary>
        /// 通知文号
        ///</summary>
        [MaxLength(50)]
        public string noticeNo { get; set; }

        ///<summary>
        /// 通知地址
        ///</summary>
        [MaxLength(200)]
        public string noticeUrl { get; set; }

        ///<summary>
        /// 申请指南地址
        ///</summary>
        [MaxLength(200)]
        public string appGuideUrl { get; set; }

        ///<summary>
        /// 公示名单地址
        ///</summary>
        [MaxLength(200)]
        public string publicItemUrl { get; set; }

        ///<summary>
        /// 是否配置阶段
        ///</summary> 
        public bool hasStage { get; set; }
        ///<summary>
        /// 联系人
        ///</summary> 

        [MaxLength(50)]
        public string linkName { get; set; }
        [MaxLength(100)]
        public string linkTel { get; set; }

        ///<summary>
        /// 政策文件路径
        [MaxLength(300)]
        public string policyPath { get; set; }
    }
}