using System;
using System.ComponentModel.DataAnnotations;
namespace MSDB.Models
{
    public class policyContent
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 标题
        /// </summary>
        [Required, MaxLength(200)]
        public string title { get; set; }
        /// <summary>
        /// 来源
        /// </summary>
        public int sourceId { get; set; }
        /// <summary>
        /// 发布日期
        /// </summary>
        public DateTime issueDate { get; set; }
        /// <summary>
        /// 内容地址
        /// </summary>
        [Required, MaxLength(500)]
        public string contentUrl { get; set; }

        public DateTime createTime { get; set; }
    }
}