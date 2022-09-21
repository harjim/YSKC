using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 项目表
    /// </summary>
    public class p_project : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 项目名
        /// </summary>
        [MaxLength(200)]
        public string pname { get; set; }
        /// <summary>
        /// RD序号
        /// </summary>
        public int rdIndex { get; set; }

        /// <summary>
        ///开始日期 
        /// </summary>
        public DateTime beginDate { get; set; }
        /// <summary>
        /// 结束日期
        /// </summary>
        public DateTime endDate { get; set; }
        /// <summary>
        /// 开始年份
        /// </summary>
        public int beginYear { get; set; }
        /// <summary>
        /// 结束年份
        /// </summary>
        public int endYear { get; set; }
        /// <summary>
        /// 负责人工号
        /// </summary>
        [Required, MaxLength(30)]
        public string masterENumber { get; set; }

        /// <summary>
        /// 高新技术领域
        /// </summary>
        [MaxLength(20)]
        public string tecIndustry { get; set; }
        /// <summary>
        /// 预计总费用
        /// </summary>
        public int? estimateExpense { get; set; }

        public int companyId { get; set; }


        /// <summary>
        /// 研发部门ID
        /// </summary>
        public int? rdDeptId { get; set; }

        /// <summary>
        /// 项目编号
        /// </summary>
        [MaxLength(50)]
        public string rdNumber { get; set; }

        /// <summary>
        /// 项目来源
        /// 1.本企业自选项目；
        /// 2.政府部门科技项目；
        /// 3.其他企业（单位）委托项目；
        /// 4.境外项目；
        /// 5.其他项目。
        /// </summary>
        public int projectSource { get; set; }
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
        public int formula { get; set; }
        /// <summary>
        /// 项目当年成果形式
        /// 11.论文或专著；
        /// 12.带有技术、工艺参数的图纸、技术标准、操作规范、技术论证、研究报告、咨询评价；
        /// 21.自主研制的新产品原型或样机、样件、样品、配方、新装置；
        /// 22.自主开发的新技术或新工艺、新工法、新服务；
        /// 23.对已有产品、工艺等实现突破性变革；
        /// 24对已有产品、工艺等进行一般性改进；
        /// 25.新产品、新工艺等推广与示范活动；
        /// 31.发明专利；
        /// 32.实用新型专利或外观设计专利；
        /// 41.基础软件；
        /// 42.中间件或新算法；
        /// 43.应用软件；
        /// 44.软件著作权；
        /// 50.其他。
        /// </summary>
        
        [Required,MaxLength(10)]
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
        public int targets { get; set; }
        /// <summary>
        /// 政府资金
        /// </summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal govCost { get; set; }
        /// <summary>
        /// 是否试制
        /// </summary>
        public bool trialProd { get; set; }
        /// <summary>
        /// 试制开始日期 
        /// </summary>
        public DateTime? tBeginDate { get; set; }
        /// <summary>
        /// 试制结束日期
        /// </summary>
        public DateTime? tEndDate { get; set; }

        /// <summary>
        /// 部门
        /// </summary>
        [MaxLength(200)]
        public string deptName { get; set; }
        /// <summary>
        /// 车间
        /// </summary>
        [MaxLength(200)]
        public string workshop { get; set; }
        /// <summary>
        /// 产线
        /// </summary>
        [MaxLength(200)]
        public string productLine { get; set; }
        /// <summary>
        /// 工艺段
        /// </summary>
        [MaxLength(200)]
        public string processSection { get; set; }

        public bool hasChild { get; set; }


        public int parentId { get; set; }

        [Required, MaxLength(50)]
        public string rdTitle { get; set; }
        /// <summary>
        /// 涉及产品
        /// </summary>
        [MaxLength(200)]
        public string involvedProduct { get; set; }
        
        /// <summary>
        /// 部门ID
        /// </summary>
        public int? deptId { get; set; }

    }
}
