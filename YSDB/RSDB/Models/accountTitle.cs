using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 科目表
    /// </summary>
    public class accountTitle
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 科目编码
        /// </summary>
        [Required,MaxLength(50)]
        public string accountNumber { get; set; }
        /// <summary>
        /// 科目名
        /// </summary>
        [Required, MaxLength(200)]
        public string accountName { get; set; }
        /// <summary>
        /// 科目全名。父名称-子名称
        /// </summary>
        [MaxLength(200)]
        public string fullAccountName { get; set; }
        /// <summary>
        /// 上级科目Id
        /// </summary>
        public int parentId { get; set; }
        /// <summary>
        /// 科目类别
        /// 1、资产类
        /// 2、负债类
        /// 3、权益类
        /// 4、成本类
        /// 5、损益类
        /// </summary>
        public int accoutType { get; set; }

        public int companyId { get; set; }

        public int creatorId { get; set; }
        public DateTime createTime { get; set; }

        /// <summary>
        /// 最后操作人id
        /// </summary>
        public int lastUpdatorId { get; set; }
        /// <summary>
        /// 最后更新时间
        /// </summary>
        public DateTime lastUpdateTime { get; set; }

        public int level { get; set; }
    }
}
