using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 问题总结
    /// </summary>
    public class p_problem_summary : tablebase
    {
         [Key]
        public int id { get; set; }

        public int situationId { get; set; }

          /// <summary>
        /// 遇到的问题
        /// </summary>
        [MaxLength(500)]
        public string problem { get; set; }

        /// <summary>
        /// 解决方案
        /// </summary>
        [MaxLength(500)]
        public string solution { get; set; }
    }
}