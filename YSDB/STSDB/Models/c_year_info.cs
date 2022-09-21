using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace STSDB.Models
{
    public class c_year_info : tablebase
    {
        [Key]
        public int id { get; set; }

        public int companyId { get; set; }

        public int year { get; set; }

        /// <summary>
        /// 营业收入（万元）
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal businessIncome { get; set; }

        /// <summary>
        /// 净资产（万元）
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal netAssets { get; set; }

        /// <summary>
        /// 利润总和（万元）
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal totalProfit { get; set; }

        /// <summary>
        /// 进出口总额（万元）(移除)
        /// </summary>
        //   [Required, Column(TypeName = "decimal(18,2)")]
        //  public decimal importAndExport { get; set; }

        /// <summary>
        /// 固定资产
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal rdFiexdAssets { get; set; }
        /// <summary>
        /// 其中：仪器和设备（万元）
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal equipmentAssets { get; set; }

        /// <summary>
        /// 高新技术产品收入（万元）
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal highTechIncome { get; set; }

        /// <summary>
        /// 从业人员数
        /// </summary>
        public int employeeNum { get; set; }

        /// <summary>
        /// 研发人数
        /// </summary>
        public int rdEmployeeNum { get; set; }

        /// <summary>
        ///研发开发机构数
        /// </summary>
        public int rdOrgNum { get; set; }

        /// <summary>
        ///其中，大专及以上学历（位）人员
        /// </summary>
        public int collegeAboveNum { get; set; }

        /// <summary>
        ///专利申请数
        /// </summary>
        public int patentNum { get; set; }

        /// <summary>
        ///其中发明专利
        /// </summary>
        public int inventionPatentNum { get; set; }

        /// <summary>
        /// 期末有效发明专利数
        /// </summary>
        public int validPatentNum { get; set; }

        /// <summary>
        /// 企业所得税(万元)
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal taxIncome { get; set; }
        
        /// <summary>
        ///实用新型数
        /// </summary>
        public int utilityPatentNum { get; set; }

        /// <summary>
        /// 外观设计数
        /// </summary>
        public int designPatentNum { get; set; }

        /// <summary>
        /// 其中计算机软件著作数
        /// </summary>
        public int softNum { get; set; }

        /// <summary>
        /// 年度授权数
        /// </summary>
        public int authIpNum { get; set; }
        ///<summary>
        /// 占营收比（研发占比）
        /// 计算方式：年研发金额/年营业收入*100%
        ///</summary>
        [Column(TypeName="decimal(18,2)")]
        public decimal rdRatio { get; set; }
    }
}
