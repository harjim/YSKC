using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace STSDB.Models
{
    public class c_project : tablebase
    {
        [Key]
        public int id { get; set; }

        public int companyId { get; set; }

        ///<summary>
        /// 项目名称(全公司不可重复)
        ///</summary>
        [Required, MaxLength(100)]
        public string pname { get; set; }

        ///<summary>
        /// 项目编号(RD)
        ///</summary>
        [Required, MaxLength(50)]
        public string rdTitle { get; set; }

        /// <summary>
        /// 项目来源
        /// 1.本企业自选项目；
        /// 2.政府部门科技项目；
        /// 3.其他企业（单位）委托项目；
        /// 4.境外项目；
        /// 5.其他项目。
        /// </summary>
        public int? projectSource { get; set; }

        /// <summary>
        /// 项目开展形式
        ///  10.自主完成；
        ///  21.与境内研究机构合作；
        ///  22.与境内高等学校合作；
        ///  23.与境内其他企业或单位合作；
        ///  24.与境外机构合作；
        ///  30.委托其他企业或单位；
        ///  40.其他形式。
        /// </summary>
        public int? formula { get; set; }

        /// <summary>
        /// 项目当年成果形式
        /// 01.论文或专著
        /// 02.新产品、新工艺等推广与示范活动
        /// 03.对已有产品、工艺等进行一般性改进
        /// 04.对已有产品、工艺等实现突破性变革
        /// 05.软件著作权
        /// 06.应用软件
        /// 07.中间件或新算法
        /// 08.基础软件
        /// 09.发明专利
        /// 10.实用新型专利或外观设计专利
        /// 11.带有技术、工艺参数的图纸、技术标准、操作规范、技术论证、研究报告、咨询评价
        /// 12.自主研制的新产品原型或样机、样件、样品、配方、新装置
        /// 13.自主开发的新技术或新工艺、新工法、新服务
        /// 14.其他
        /// </summary>
        [MaxLength(10)]
        public string result { get; set; }

        /// <summary>
        /// 项目技术经济目标
        ///   1.科学原理的探索、发现；
        ///   2.技术原理的研究；
        ///   3.开发全新产品；
        ///   4.增加产品功能或提高性能；
        ///   5.提高劳动生产率；
        ///   6.减少能源消耗或提高能源使用效率；
        ///   7.节约原材料；
        ///   8.减少环境污染；
        ///   9.其他。
        /// </summary>
        public int? targets { get; set; }

        ///<summary>
        /// 开始年份
        ///</summary>
        public int beginYear { get; set; }

        ///<summary>
        /// 结束年份
        ///</summary>
        public int endYear { get; set; }

        ///<summary>
        /// 开始日期
        ///</summary>
        public DateTime beginDate { get; set; }

        ///<summary>
        /// 结束日期
        ///</summary>
        public DateTime endDate { get; set; }
        /// <summary>
        /// 预计总费用(万元)
        /// totalBudget = Sum(selfRaised,govFunds,outsideFunds,otherChannels)
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? totalBudget { get; set; }
        
        /// <summary>
        /// 自筹金额(万元)
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? selfRaised { get; set; }
        ///<summary>
        /// 政府部门资金(万元)
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? govFunds { get; set; }
        ///<summary>
        /// 外部单位资金(万元)
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? outsideFunds { get; set; }
        ///<summary>
        /// 其他渠道(万元)
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? otherChannels { get; set; }
        

    }
}
