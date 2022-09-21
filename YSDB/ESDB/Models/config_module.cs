using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace ESDB.Models
{
    public class config_module
    {
        [Key]
        public int id { get; set; }

        [Required, MaxLength(20)]
        public string moduleName { get; set; }
        [Required, MaxLength(200)]
        public string iconPath { get; set; }
        [Required, MaxLength(200)]
        public string activeIconPath { get; set; }
        [Required, MaxLength(200)]
        public string imagePath { get; set; }
        [Required, MaxLength(300)]
        public string description { get; set; }
        public int order { get; set; }

        /// <summary>
        ///状态: 1：禁用  0 启用(默认为0)
        /// </summary>
        public bool disabled { get; set; }
        /// <summary>
        /// 创建人
        /// </summary>
        public int msCreatorId { get; set; }
        /// <summary>
        /// 更新人
        /// </summary>
        public int msLastUpdatorId { get; set; }
        /// <summary>
        /// 创建时间
        /// </summary>
        public DateTime createTime { get; set; }
        public DateTime lastUpdateTime { get; set; }
    }
}