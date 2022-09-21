using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 过程文件数据项
    /// </summary>
    public class docFileItem
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 数据项Key
        /// </summary>
        [Required,MaxLength(20)]
        public string itemKey { get; set; }
        /// <summary>
        /// 默认数据标题，后面可以给用户自己配置不同模板
        /// </summary>
        [Required,MaxLength(50)]
        public string itemTitle { get; set; }
        /// <summary>
        /// 数据项模式，只显示，可修改
        /// </summary>
        public int itemMode { get; set; }
        /// <summary>
        /// 数据类型,根据不同类型显示不同控件
        /// </summary>
        public string itemType { get; set; }
        /// <summary>
        /// 是否必输
        /// </summary>
        public bool required { get; set; }
        /// <summary>
        /// 关联过程文件Id
        /// </summary>
        public int docFileId { get; set; }
        /// <summary>
        /// 创建用户
        /// </summary>
        public int msCreatorId { get; set; }
        /// <summary>
        /// 创建时间
        /// </summary>
        public DateTime createTime { get; set; }
        /// <summary>
        /// 更新用户
        /// </summary>
        public int msLastUpdatorId { get; set; }
        /// <summary>
        /// 更新时间
        /// </summary>
        public DateTime lastUpdateTime { get; set; }
    }
}