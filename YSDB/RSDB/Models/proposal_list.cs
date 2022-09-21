using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 提案列表
    /// </summary>
    public class proposal_list : tablebase
    {
        [Key]
        public int id { get; set; }

        public int companyId { get; set; }
        ///<summary>
        /// RD
        ///</summary>
        public int? projectId { get; set; }
        ///<summary>
        /// 提案日期
        ///</summary>
        public DateTime proposalDate { get; set; }
        /// <summary>
        /// 项目名
        /// </summary>
        [MaxLength(200), Required]
        public string pname { get; set; }
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
        /// 立项部门
        /// </summary>
        [MaxLength(200), Required]
        public string deptName { get; set; }
        /// <summary>
        /// 项目开始日期
        /// </summary>
        public DateTime beginDate { get; set; }
        /// <summary>
        /// 项目结束日期
        /// </summary>
        public DateTime endDate { get; set; }
        /// <summary>
        /// 试制开始日期 
        /// </summary>
        public DateTime? tBeginDate { get; set; }
        /// <summary>
        /// 试制结束日期
        /// </summary>
        public DateTime? tEndDate { get; set; }

        /// <summary>
        /// 项目提出人
        /// </summary>
        [MaxLength(50), Required]
        public string initiator { get; set; }
        /// <summary>
        /// 项目对应产品
        /// </summary>
        [MaxLength(200)]
        public string involvedProduct { get; set; }
        ///<summary>
        /// 项目人员名单
        ///</summary>
        [MaxLength(500)]
        public string members { get; set; }
        ///<summary>
        /// 主要设备仪器
        ///</summary>
        [MaxLength(500)]
        public string equipments { get; set; }
        ///<summary>
        /// 现状分析
        ///</summary>
        [MaxLength(2000), Required]
        public string situation { get; set; }

        ///<summary>
        /// 创新点
        ///</summary>
        [Column(TypeName = "text")]
        public string innovation { get; set; }

        ///<summary>
        /// 主要研究内容
        ///</summary>
        [Required, Column(TypeName = "text")]
        public string research { get; set; }
        ///<summary>
        /// 实施前后对比
        ///</summary>
        [MaxLength(800)]
        public string comparison { get; set; }
        ///<summary>
        /// 进度计划
        ///</summary>
        [MaxLength(800)]
        public string progressPlan { get; set; }

        ///<summary>
        /// 技术指标及经济效益
        ///</summary>
        [MaxLength(800), Required]
        public string target { get; set; }
    }
}
