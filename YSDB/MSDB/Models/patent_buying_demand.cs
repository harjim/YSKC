using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    /// <summary>
    /// 专利购买需求表
    /// </summary>
    public class patent_buying_demand: tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 客户id
        /// </summary>
        public int customerId { get; set; }
        /// <summary>
        /// 状态:0 未提交，1：提交（确定业务员），2：确认，3：完成
        /// </summary>
        public int status { get; set; }
        /// <summary>
        /// 需求文档
        /// </summary>
        [MaxLength(1000)]
        public string filePath { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [MaxLength(500)]
        public string remark { get; set; }
        /// <summary>
        /// 发明专利需求数量
        /// </summary>
        public int? inventionNum { get; set; }
        /// <summary>
        /// 实用新型需求数量
        /// </summary>
        public int? utilityNum { get; set; }
        /// <summary>
        /// 外观设计需求数量
        /// </summary>
        public int? appearanceDesignNum { get; set; }
        /// <summary>
        /// 发明人信息
        /// </summary>
        [MaxLength(1000)]
        public string inventorInfo { get; set; }
        /// <summary>
        /// 业务员
        /// </summary>
        public int? ownerId { get; set; }
        /// <summary>
        /// 提交时间
        /// </summary>
        public DateTime? submitTime { get; set;}
        
        /// <summary>
        /// 年份
        /// </summary>
        public int year { get; set; }
        
        /// <summary>
        /// 类型 0:购买 1:撰写
        /// </summary>
        public int type { get; set; }
        /// <summary>
        /// 工程师id
        /// </summary>
        public int engineerId { get; set; }

    }
}
