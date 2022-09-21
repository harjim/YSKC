using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class p_achievement_file : tablebase
    {
        [Key]
        public int id { get; set; }
        public int companyId { get; set; }
        ///<summary>
        /// 成果id
        ///</summary>
        public int achievementId { get; set; }
        public int listId { get; set; }

        /// <summary>
        /// 文件路径
        /// </summary>
        [Required, MaxLength(200)]
        public string filepath { get; set; }
        /// <summary>
        /// 文件名称
        /// </summary>
        [Required, MaxLength(100)]
        public string fileName { get; set; }

        ///<summary>
        /// 阶段key
        ///</summary>
        [MaxLength(50)]
        public string stageKey { get; set; }
        ///<summary>
        /// 排序列
        ///</summary>
        public int seq { get; set; }
        ///<summary>
        /// 转化结果：0：新产品，1：新设备，2：新技术服务，3：样品/样机，4：新服务
        ///</summary>
        public int converResult { get; set; }
    }
}