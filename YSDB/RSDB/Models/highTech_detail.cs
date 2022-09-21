using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 高新技术明细
    /// </summary>
    public class highTech_detail : tablebase
    {
        [Key]
        public int id { get; set; }
        public int companyId { get; set; }

        public int highTechId { get; set; }
        /// <summary>
        /// 同类产品是否已有技术标准 [有，无]
        /// </summary>
        public bool hasSameTechStandard { get; set; }

        /// <summary>
        /// 技术标准 [0:国际标准,1:国家标准,2:行业标准,3:企业标准,4:其他]
        /// </summary>
        [MaxLength(20)]
        public string techStandard { get; set; }
        /// <summary>
        /// 其他标准,当技术标准[standard]选[4:其他]时，可输入该项。
        /// </summary>
        [MaxLength(20)]
        public string otherTechStandard { get; set; }
        /// <summary>
        /// 新产品是否制定质量标准[有，无]
        /// </summary>
        public bool hasQualityStandard { get; set; }
        /// <summary>
        /// 质量标准,当新产品是否制定质量标准[hasQualityStandard]为true时，可输入该项。
        /// </summary>
        [MaxLength(50)]
        public string qualityStandard { get; set; }
        /// <summary>
        /// 新产品是否通过鉴定或第三方检测 [是，否]
        /// </summary>
        public bool passTest { get; set; }
        /// <summary>
        /// 新产品技术标准指标或鉴定指标是否达到国内外先进技术标准的水平
        /// [0:全部指标达国际标准水平, 1;部分指标达国际标准水平, 2:全部指标达国内标准先进水平,
        ///  3:部分指标达国内先进标准水平, 4:仅达到国内产品基本标准要求水平]
        /// </summary>
        public int techLevel { get; set; }
        /// <summary>
        /// 新产品是否国内首创新产品 [是，否]
        /// </summary>
        public bool domesticFirst { get; set; }
        /// <summary>
        /// 技术指标是否达到国际标准水平 [0:全部指标达国际标准先进水平,1:部分指标达国际标准先进水平,2全部指标与国际标准先进水平存在差距]
        /// </summary>
        public int internationalLevel { get; set; }
        /// <summary>
        /// 项目来源 [0:国家计划,1:部门计划,2:省计划,3:市计划,4自行开发]
        /// </summary>
        public int projectSource { get; set; }
        /// <summary>
        /// 技术来源 [0:国外技术,1:国内高校、科研院所技术,2:自有技术]
        /// </summary>
        public int techSource { get; set; }
        /// <summary>
        /// 产品开发形式 [0:合作开发,1:独立开发,2:消化引进吸收,3:其它]
        /// </summary>
        public int devType { get; set; }
        /// <summary>
        /// 本高新技术产品先进性说明
        /// </summary>
        [MaxLength(2000)]
        public string advancedExplain { get; set; }
        /// <summary>
        /// 相关的专利
        /// </summary>
        [MaxLength(1000)]
        public string patents { get; set; }
        /// <summary>
        /// 应用专利关键技术
        /// </summary>
        [MaxLength(2000)]
        public string patentsTech { get; set; }
        /// <summary>
        /// 新产品与国际标准、国内标准（国标、行标、先进企业标准）具体技术指标数据比较情况。
        /// 主要技术指标或与之前技术对比
        /// </summary>
        [MaxLength(2000)]
        public string techCompare { get; set; }
        /// <summary>
        /// 与同类产品（服务）或研究之前产品的优势
        /// </summary>
        [MaxLength(2000)]
        public string advantage { get; set; }
        /// <summary>
        /// 高新技术产品认定或第三方评价
        /// </summary>
        [MaxLength(2000)]
        public string evaluate { get; set; }
        /// <summary>
        /// 产品对产业发展的影响和贡献情况
        /// </summary>
        [MaxLength(2000)]
        public string contribution { get; set; }
        /// <summary>
        ///自主知识产权含量 [0:核心技术,1:较高,2:一般,3:无]
        /// </summary>
        public int ownerProperty { get; set; }
        /// <summary>
        ///创新性 [0:首创,1:重大改进,2:较大改进,3:消化吸收]
        /// </summary>
        public int innovation { get; set; }
        /// <summary>
        ///先进性 [0:国际领先,1:国际先进,2:国内领先,3:国内先进,4:省内先进]
        /// </summary>
        public int advanced { get; set; }
        /// <summary>
        ///成熟度 [0:产品样机（样品）,1:小批量生产,2:批量（规模）生产]
        /// </summary>
        public int maturity { get; set; }
        
        /// <summary>
        ///产品获奖情况 [0:国家级,1:部、省级,2:市级,3:其它,4:无]
        /// </summary>
        public int award { get; set; }
    }
}
