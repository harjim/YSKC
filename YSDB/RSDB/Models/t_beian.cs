using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 备案表
    /// </summary>
    public class t_beian : tablebase
    {
        [Key]
        public int id { get; set; }

        public int companyId { get; set; }

        public int year { get; set; }

        ///<summary>
        /// ms项目id
        ///</summary>
        public int sourceProjectId { get; set; }

        ///<summary>
        /// ms项目类型id
        ///</summary>
        public int productId { get; set; }

        ///<summary>
        /// 备案证号
        ///</summary>
        [MaxLength(50)]
        public string beianNo { get; set; }

        ///<summary>
        /// 项目编号
        ///</summary>
        [MaxLength(50)]
        public string projectNo { get; set; }

        ///<summary>
        /// 备案单位(客户名称)
        ///</summary>
        [MaxLength(50)]
        public string applicant { get; set; }

        ///<summary>
        /// 项目名称
        ///</summary>
        [MaxLength(200)]
        public string pname { get; set; }

        ///<summary>
        /// 经济类型： 0 有限责任公司， 1股份有限公司
        ///</summary>
        public int? economyType { get; set; }

        ///<summary>
        /// 备案日期【备案签发日期】
        ///</summary>
        public DateTime? beianDate { get; set; }

        ///<summary>
        /// 建设地点，多个[数组格式]
        ///</summary>
        [MaxLength(500)]
        public string constructionPlace { get; set; }

        ///<summary>
        /// 计划开始日期
        ///</summary>
        public DateTime? beginDate { get; set; }

        ///<summary>
        /// 计划结束日期
        ///</summary>
        public DateTime? endDate { get; set; }

        ///<summary>
        /// 备案文件
        ///</summary>
        [MaxLength(500)]
        public string filePath { get; set; }

        ///<summary>
        /// 备案证扫描文件
        ///</summary>
        [MaxLength(500)]
        public string scanFilePath { get; set; }

        ///<summary>
        /// 主要内容
        ///</summary>
        [MaxLength(2000)]
        public string content { get; set; }

        ///<summary>
        /// 变更次数(默认为0)
        ///</summary>
        public int changedCnt { get; set; }

        ///<summary>
        /// 变更日期(变更记录的日期串，多个逗号分割)
        ///</summary>
        [MaxLength(100)]
        public string changedDates { get; set; }

        ///<summary>
        /// 备案账户
        ///</summary>
        [MaxLength(20)]
        public string accountName { get; set; }

        ///<summary>
        /// 备案密码
        ///</summary>
        [MaxLength(50)]
        public string accountPassword { get; set; }

        ///<summary>
        /// 备案资产项数(设备数)
        ///</summary>
        public int? equipmentCnt { get; set; }

        ///<summary>
        /// 备案资产数量(sum(t_equipment.quantity))
        ///</summary>
        public int? equipmentQuantity { get; set; }

        ///<summary>
        ///项目用电(万kw·h)
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? powerUsed { get; set; }

        ///<summary>
        /// 项目用能(tce）
        /// energyUsed = powerUsed * 1.229
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? energyUsed { get; set; }

        [MaxLength(200)]
        public string remark { get; set; }

        //////////////////////////////////////////////////////////////////////////////////
        /* 费用相关，转入t_beian_summary,以下字段已废弃 */
        ///<summary>
        /// 总金额
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? totalAmount { get; set; }

        ///<summary>
        /// 设备
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? equipment { get; set; }

        ///<summary>
        /// 铺底流动资金
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? initWorkCapital { get; set; }

        ///<summary>
        /// 建设费
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? construction { get; set; }

        ///<summary>
        /// 完成情况
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? completeRate { get; set; }

        ///<summary>
        /// 已完成设备费
        ///</summary>
        [Column(TypeName = "decimal(18,4)")]
        public decimal? equipmentCost { get; set; }

        ///<summary>
        /// 已完成建设费
        ///</summary>
        [Column(TypeName = "decimal(18,4)")]
        public decimal? constructionCost { get; set; }

        ///<summary>
        /// 已完成铺底流动资金
        ///</summary>
        [Column(TypeName = "decimal(18,4)")]
        public decimal? initWorkCapitalCost { get; set; }

        //////////////////////////////////////////////////////////////////
        /* change相关转至 t_beian_changed表,以下字段已废弃*/
        public bool change { get; set; }

        ///<summary>
        /// 变更函签发时间
        ///</summary>
        public DateTime? changeLetterDate { get; set; }

        ///<summary>
        /// 变更内容
        ///</summary>
        [MaxLength(2000)]
        public string changeContent { get; set; }

        ///<summary>
        /// 变更涵
        ///</summary>
        [MaxLength(200)]
        public string changeFilePath { get; set; }
    }
}
