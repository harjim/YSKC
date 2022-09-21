using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    /// <summary>
    /// 专利公海
    /// </summary>
    public class patent_sea: tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 需求id
        /// </summary>
        public int? demandId { get; set; }
         [Required, MaxLength(50)]
        public string patentNo { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        [MaxLength(200)]
        public string patentName { get; set; }
        /// <summary>
        /// 申请日期
        /// </summary>
        public DateTime? applyDateTime { get; set; }
        /// <summary>
        /// 发明人
        /// </summary>
        [MaxLength(200)]
        public string inventor { get; set; }
        
        ///<summary>
        /// 类型
        ///</summary>
        public int? mainType { get; set; }
        /// <summary>
        /// 附件
        /// </summary>
        [MaxLength(1000)]
        public string filePath { get; set; }
    }
}
