using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    /// <summary>
    /// 专利购买列表
    /// </summary>
    public class patent_buying_list: tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 专利可购买id
        /// </summary>
        public int patentSeaId { get; set; }
        /// <summary>
        /// 附件
        /// </summary>
        [MaxLength(1000)]
        public string filePath { get; set; }
        /// <summary>
        /// 购买状态 0:未购买 1：已选择 2：已购买
        /// </summary>
        public int status { get; set; }
        /// <summary>
        /// 客户id
        /// </summary>
        public int customerId { get; set; }
        /// <summary>
        /// 需求id
        /// </summary>
        public int demandId { get; set; }
        
        [MaxLength(1000)]
        public string sellFile { get; set; }
    }
}
