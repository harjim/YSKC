using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 技改项目投资情况
    /// </summary>
    public class t_projectInvestMent: tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 项目id，t_project
        /// </summary>
        public int projectId { get; set; }
        /// <summary>
        /// 公司id
        /// </summary>
        public int companyId { get; set; }
        /// <summary>
        /// 设备购置费(申报金额)
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal equipmentCostOfReport { get; set; }

        /// <summary>
        /// 设备购置费(已付金额)
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal equipmentCostOfPaid { get; set; }
        /// <summary>
        /// 安装工程费(申报金额)
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal installationCostOfReport { get; set; }
        /// <summary>
        /// 安装工程费(已付金额)
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal installationCostOfPaid { get; set; }
        /// <summary>
        /// 建筑工程费(申报金额)
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal constructionCostOfReport { get; set; }
        /// <summary>
        /// 建筑工程费(已付金额)
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal constructionCostOfPaid { get; set; }
        /// <summary>
        /// 配套软件(申报金额)
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal softwareKitOfReport { get; set; }
        /// <summary>
        /// 配套软件(已付金额)
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal softwareKitOfPaid { get; set; }
        /// <summary>
        /// 检测维护(申报金额)
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal inspectionOfReport { get; set; }
        /// <summary>
        /// 检测维护(已付金额)
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal inspectionOfPaid { get; set; }
        /// <summary>
        /// 数字化集成(申报金额)
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal digitalIntegrationOfReport { get; set; }
        /// <summary>
        ///  数字化集成(已付金额)
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal digitalIntegrationOfPaid { get; set; }
        /// <summary>
        /// 研发外包服务(申报金额)
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal rdOutsourcingOfReport { get; set; }
        /// <summary>
        /// 研发外包服务(已付金额)
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal rdOutsourcingOfPaid { get; set; }
        /// <summary>
        /// 节水改造投入(申报金额)
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal waterOfReport { get; set; }
        /// <summary>
        /// 节水改造投入(已付金额)
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal waterOfPaid { get; set; }
    }
}
