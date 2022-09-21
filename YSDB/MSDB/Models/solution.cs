using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class solution : tablebase
    {
        [Key]
        public int id { get; set; }
        ///<summary>
        /// 标题
        ///</summary>
        [Required, MaxLength(300)]
        public string title { get; set; }
        ///<summary>
        /// 客户名称
        ///</summary>
        [MaxLength(200)]
        public string customer { get; set; }
        ///<summary>
        /// 服务项目
        ///</summary>
        [MaxLength(200)]
        public string serveProject { get; set; }
        ///<summary>
        /// 行业
        ///</summary>
        [MaxLength(100)]
        public string industry { get; set; }
        ///<summary>
        /// 内容，解决方案
        ///</summary>
        [Required, Column(TypeName = "text")]
        public string content { get; set; }

        ///<summary>
        /// 类型：0解决方案，1：基本知识 :default:0
        ///</summary>
        public int type { get; set; }

        ///<summary>
        /// 星级：最大值10 默认0
        ///</summary>
        public int rate { get; set; }

        ///<summary>
        /// 评星人
        ///</summary>
        public int? rateUserId { get; set; }

        ///<summary>
        /// 评星时间
        ///</summary>
        public DateTime? rateTime { get; set; }

        ///<summary>
        /// 附件
        ///</summary>
        [MaxLength(1000)]
        public string filePath { get; set; }

        ///<summary>
        /// 描述
        ///</summary>
        [MaxLength(1000)]
        public string description { get; set; }

    }
}
