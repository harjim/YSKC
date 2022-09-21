using System;
using System.ComponentModel.DataAnnotations;

namespace RSDB.Models
{
    public class c_stage : tablebase
    {
        [Key]
        public int id { get; set; }
        public int companyId { get; set; }
        /// <summary>
        /// 阶段类型
        /// </summary>
        [Required,MaxLength(10)]
        public string stageKey { get; set; }
        /// <summary>
        /// 阶段名
        /// </summary>
        [MaxLength(50)]
        public string stageName { get; set; }
    }
}