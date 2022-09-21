using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace ESDB.Models
{
    /// <summary>
    /// 文章管理
    /// </summary>
    public class article
    {
        [Key]
        public int id { get; set; }
        ///<summary>
        ///标题
        ///</summary>
        [Required, MaxLength(100)]
        public string title { get; set; }

        ///<summary>
        ///分类 0：公司新闻，1：行业动态
        ///</summary>
        public int type { get; set; }
        ///<summary>
        ///作者
        ///</summary>
        [Required, MaxLength(50)]
        public string author { get; set; }
        ///<summary>
        ///来源
        ///</summary>
        [MaxLength(100)]
        public string source { get; set; }
        ///<summary>
        ///内容
        ///</summary>
        [Required, Column(TypeName = "text")]
        public string content { get; set; }
        ///<summary>
        ///发布时间
        ///</summary>
        public DateTime issueDate { get; set; }
        
        ///<summary>
        ///置顶(顶部显示，top值越大，越排前)
        ///</summary>
        public int top { get; set; }


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
