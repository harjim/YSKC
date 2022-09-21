using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 过程文件阶段配置
    /// </summary>
    public class docFileStage: tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 阶段key
        /// </summary>
        [Required,MaxLength(20)]
        public string stageKey { get; set; }
        /// <summary>
        /// 过程文件Id
        /// </summary>
        public int docFileId { get; set; }
        /// <summary>
        /// 序号
        /// </summary>
        public int seq { get; set; }
        /// <summary>
        /// 是否自动添加
        /// </summary>
        public bool autoAdd { get; set; }
        /// <summary>
        /// 是否按月添加
        /// </summary>
        public bool monthly { get; set; }
        /// <summary>
        /// 文件名是否加月份前缀
        /// </summary>
        public bool mPrefix { get; set; }
        /// <summary>
        /// 是否需要编辑
        /// </summary>
        public bool needEdit { get; set; }

    }
}