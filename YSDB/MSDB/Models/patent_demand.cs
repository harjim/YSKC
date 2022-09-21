using System;
using System.ComponentModel.DataAnnotations;

namespace MSDB.Models
{
    /// <summary>
    /// 专利需求表
    /// </summary>
    public class patent_demand
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 需求编号,格式yyyymmdd001
        /// </summary>
        [MaxLength(12)]
        public string demandNo { get; set; }
        /// <summary>
        /// 客户id
        /// </summary>
        public int customerId { get; set; }
        /// <summary>
        /// 年份
        /// </summary>
        public int year { get; set; }
        /// <summary>
        /// 需求类型 0:购买 1:撰写
        /// </summary>
        public int type { get; set; }
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
        /// 软件著作权数量
        /// </summary>
        public int? softRightNum { get; set; }
        /// <summary>
        /// 总数
        /// </summary>
        public int total { get; set; }
        /// <summary>
        /// 业务员
        /// </summary>
        public int? ownerId { get; set; }
        /// <summary>
        /// 技术人员
        /// </summary>
        public int techId { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [MaxLength(200)]
        public string remark { get; set; }
        /// <summary>
        /// 委托书.
        /// </summary>
        [MaxLength(500)]
        public string proxyPath { get; set; }
        /// <summary>
        /// 营业执照
        /// </summary>
        [MaxLength(500)]
        public string blPath { get; set; }
        /// <summary>
        /// 减免
        /// </summary>
        [MaxLength(500)]
        public string remissionPath { get; set; }
        /// <summary>
        /// 其它资料
        /// </summary>
        [MaxLength(500)]
        public string otherPath { get; set; }

        /// <summary>
        /// 计划提交国知局日期
        /// </summary>
        public DateTime planSubmitDate { get; set; }
        /// <summary>
        /// 创建时间
        /// </summary>
        public DateTime createTime { get; set; }
    }
}