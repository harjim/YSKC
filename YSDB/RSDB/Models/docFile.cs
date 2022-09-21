using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 过程文件
    /// </summary>
    public class docFile
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 文件名
        /// </summary>
        [Required,MaxLength(50)]
        public string docName { get; set; }
        /// <summary>
        /// 是否必选
        /// </summary>
        public bool required { get; set; }
        /// <summary>
        /// 是否可以多个
        /// </summary>
        public bool multiple { get; set; }
        /// <summary>
        /// 是否启用.
        /// </summary>
        public bool enabled { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [MaxLength(200)]
        public string remark { get; set; }
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

         /// <summary>
        /// 0 技术编写 1 财务编写
        /// </summary>
        public int owner { get; set; }
         /// <summary>
        /// 是否需要编辑
        /// </summary>
        public bool needEdit { get; set; }
        /// <summary>
        /// 0 不限制 1阶段唯一 2 月份唯一 3年唯一 4项目唯一
        /// </summary>
        public int uniqueness { get; set; }
    }
}
