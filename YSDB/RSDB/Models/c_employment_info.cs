using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{

    ///<summary>
    /// 从业人员情况
    ///</summary>
    public class c_employment_info : tablebase
    {
        [Key]
        public int id { get; set; }
        public int companyId { get; set; }
        public int year { get; set; }
        ///<summary>
        /// 法人
        ///</summary>
        [MaxLength(30)]
        public string corporationName { get; set; }
        ///<summary>
        /// 法人学历
        ///</summary>
        public int? corporationDegree { get; set; }
        ///<summary>
        /// 法人联系电话
        ///</summary>
        [MaxLength(20)]
        public string corporationPhone { get; set; }
        ///<summary>
        /// 法人身份证号码
        ///</summary>
        [MaxLength(30)]
        public string corporationIdNum { get; set; }
        ///<summary>
        /// 联系人
        ///</summary>
        [MaxLength(30)]
        public string linkName { get; set; }
        ///<summary>
        /// 联系人学历
        ///</summary>
        public int? linkDegree { get; set; }
        ///<summary>
        /// 联系人电话
        ///</summary>
        [MaxLength(20)]
        public string linkPhone { get; set; }
        ///<summary>
        /// 联系人身份证号码
        ///</summary>
        [MaxLength(30)]
        public string linkIdNum { get; set; }
        ///<summary>
        /// 入职人数
        ///</summary>
        public int? employees { get; set; }
        ///<summary>
        /// 女职工人数
        ///</summary>
        public int? females { get; set; }
        ///<summary>
        /// 留学归国人员数
        ///</summary>
        public int? returnees { get; set; }
        ///<summary>
        ///社保人员数
        ///</summary>
        public int? insurances { get; set; }
        ///<summary>
        /// 外籍专家数
        ///</summary>
        public int? foreignExperts { get; set; }
        ///<summary>
        /// 应届毕业生
        ///</summary>
        public int? freshGraduates { get; set; }
        ///<summary>
        /// 行政人员数 
        ///</summary>
        public int? administrations { get; set; }
        ///<summary>
        /// 市场营销人员数
        ///</summary>
        public int? marketings { get; set; }
        ///<summary>
        /// 研发设计人数
        ///</summary>
        public int? rdDesigners { get; set; }
        ///<summary>
        /// 加工制造人数
        ///</summary>
        public int? processes { get; set; }
        ///<summary>
        /// 其他工作人数
        ///</summary>
        public int? otherWorkers { get; set; }
        ///<summary>
        /// 博士人数
        ///</summary>
        public int? doctors { get; set; }
        ///<summary>
        /// 硕士人数
        ///</summary>
        public int? masters { get; set; }
        ///<summary>
        /// 本科人数
        ///</summary>
        public int? undergraduates { get; set; }
        ///<summary>
        /// 专科人数
        ///</summary>
        public int? juniorColleges { get; set; }
        ///<summary>
        /// 其他学历人数
        ///</summary>
        public int? otherDegrees { get; set; }
        ///<summary>
        /// 高级职称人数
        ///</summary>
        public int? highTitles { get; set; }
        ///<summary>
        /// 中级职称人数
        ///</summary>
        public int? middleTitles { get; set; }
        ///<summary>
        /// 初级职称人数
        ///</summary>
        public int? primaryTitles { get; set; }
        ///<summary>
        /// 其他职称人数
        ///</summary>
        public int? otherTitles { get; set; }
    }
}
