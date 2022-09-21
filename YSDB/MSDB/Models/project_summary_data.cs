using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    /// <summary>
    /// 项目进度数据
    /// </summary>
    public class project_summary_data
    {
        [Key]
        public int id { get; set; }

        public int customerId { get; set; }
        public int year { get; set; }

        ///<summary>
        /// rd规划数(report 表)
        /// </summary>
        public int? cnt { get; set; }

        ///<summary>
        /// rd数
        /// </summary>
        public int? rdCount { get; set; }
        ///<summary>
        ///财务总预算
        ///</summary>  
        [Column(TypeName = "decimal(18,2)")]
        public decimal? budget { get; set; }
        /// <summary>
        /// 核算类型 0:成本重分类核算研发费,1:冲减主营业务成本列支研发费
        /// </summary>
        public int? accountType { get; set; }
        /// <summary>
        /// 财务规划研发费(万元)
        /// </summary>
        public int? rdFee { get; set; }
        /// <summary>
        /// 人员总数
        /// </summary>
        public int? employeeAmount { get; set; }
        /// <summary>
        /// 营收预测（万元）
        /// fsct=Forecast
        /// </summary>
        public int? revenueFcst { get; set; }
        /// <summary>
        /// 业务预测研发费（万元）
        /// fsct=Forecast
        /// </summary>
        public int? salesRdFee { get; set; }
        /// <summary>
        /// 财务处理方式
        /// fsct=Forecast
        /// </summary>
        public int? finaMode { get; set; }
        ///<summary>
        ///研发费用
        ///</summary> 
        [Column(TypeName = "decimal(18,2)")]
        public decimal? rdFunds { get; set; }
        ///<summary>
        ///过程文档数(轨迹数)
        ///</summary>
        public int? docFileCount { get; set; }
        ///<summary>
        ///高品数
        ///</summary>
        public int? highTechCount { get; set; }
        ///<summary>
        ///高品占比
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? highTechIncome { get; set; }
        ///<summary>
        ///累计主营业务收入
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? totalIncome { get; set; }
        ///<summary>
        /// 人员数
        ///</summary>
        public int? employeeCount { get; set; }
        ///<summary>
        /// 设备数
        ///</summary>
        public int? equipmentCount { get; set; }
        ///<summary>
        /// 研发人员数
        ///</summary>
        public int? rdEmployeeCount { get; set; }
        ///<summary>
        /// 研发设备数
        ///</summary>
        public int? rdEquipmentCount { get; set; }
        ///<summary>
        /// 专利申请数
        ///</summary>
        public int? patentApplyCnt { get; set; }
        ///<summary>
        /// 上年延续RD数
        ///</summary>
        public int? lastRdCnt { get; set; }
        ///<summary>
        /// 本年rd延续下年rd数
        /// </summary>
        public int? nextRdCnt { get; set; }
        ///<summary>
        ///提案数
        ///</summary>
        public int? proposalCnt { get; set; }
        ///<summary>
        ///知识产权数(专利数)
        ///</summary>
        public int? patentCnt { get; set; }
        ///<summary>
        ///成果项数（只统计成果数）
        ///</summary>
        public int? achievementCnt { get; set; }
        ///<summary>
        ///多层级文件数统计
        ///</summary>
        public int? levelFileCnt { get; set; }
        ///<summary>
        /// 机构建设数
        ///</summary>
        public int? buildCnt { get; set; }
        ///<summary>
        /// 人员绑定数
        ///</summary>
        public int? employeeOpenidCnt { get; set; }
        ///<summary>
        /// 查新报告数
        ///</summary>
        public int? reportCnt { get; set; }
        ///<summary>
        /// 成本工资
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? c10000 { get; set; }
        ///<summary>
        /// 成本五险一金
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? c10100 { get; set; }
        ///<summary>
        /// 成本材料
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? c20000 { get; set; }
        ///<summary>
        /// 成本动力
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? c20100 { get; set; }
        ///<summary>
        /// 成本燃料
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? c20200 { get; set; }
        ///<summary>
        /// 成本试制
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? c20300 { get; set; }
        ///<summary>
        /// 成本检测
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? c20500 { get; set; }
        ///<summary>
        /// 成本修理
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? c20600 { get; set; }
        ///<summary>
        /// 成本样机
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? c20700 { get; set; }
        ///<summary>
        /// 成本设备折旧(300,301)
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? c30000 { get; set; }
        ///<summary>
        /// 成本软件摊销
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? c40000 { get; set; }
        ///<summary>
        /// 成本其他摊销(401,402)
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? c40200 { get; set; }
        ///<summary>
        /// 成本设计
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? c50000 { get; set; }
        ///<summary>
        /// 成本其他(所有>=60000)
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? c69900 { get; set; }
        ///<summary>
        /// 总计。费用总计=sum(k*)
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? costAmount { get; set; }
        ///<summary>
        ///总营收
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? revenue { get; set; }
        public DateTime createTime { get; set; }

        public DateTime updateTime { get; set; }

        public DateTime? operationTime { get; set; }
        public int? operationUserId { get; set; }
    }
}
