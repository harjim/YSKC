using System;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace RSDB.Models
{

    public class c_rd_funds
    {
        [Key]
        public int id { get; set; }

        public int companyId { get; set; }
        public int year { get; set; }
        public DateTime month { get; set; }
        ///<summary>
        /// 类型：0月份数据，1：月份累计
        ///</summary>
        public int type { get; set; }
        ///<summary>
        /// 工资
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? k10000 { get; set; }
        ///<summary>
        /// 五险一金
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? k10100 { get; set; }
        ///<summary>
        /// 材料
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? k20000 { get; set; }
        ///<summary>
        /// 动力
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? k20100 { get; set; }
        ///<summary>
        /// 燃料
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? k20200 { get; set; }
        ///<summary>
        /// 试制
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? k20300 { get; set; }
        ///<summary>
        /// 检测
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? k20500 { get; set; }
        ///<summary>
        /// 修理
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? k20600 { get; set; }
        ///<summary>
        /// 样机
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? k20700 { get; set; }
        ///<summary>
        /// 设备折旧(300,301)
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? k30000 { get; set; }
        ///<summary>
        /// 软件摊销
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? k40000 { get; set; }
        ///<summary>
        /// 其他摊销(401,402)
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? k40200 { get; set; }
        ///<summary>
        /// 设计
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? k50000 { get; set; }
        ///<summary>
        /// 其他(所有>=60000)
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? k69900 { get; set; }   
        ///<summary>
        /// 研发费总计
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? amount { get; set; }
        ///<suumary>
        ///营收总计
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? revenue { get; set; }
        public DateTime createTime { get; set; }
        public DateTime lastUpdateTime { get; set; }
    }
}