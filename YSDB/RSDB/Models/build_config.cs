using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace RSDB.Models
{
    public class build_config : tablebase
    {
        [Key]
        public int id { get; set; }

        public int companyId { get; set; }

        public int year { get; set; }
        ///<summary>
        /// 类型：0：技术 1：财务
        ///</summary>
        public int type { get; set; }
        ///<summary>
        /// 制定部门
        ///</summary>
        [MaxLength(50)]
        public string madeDept { get; set; }
        ///<summary>
        /// 制定人
        ///</summary>
        [MaxLength(30)]
        public string maker { get; set; }
        ///<summary>
        /// 发布日期
        ///</summary>
        public DateTime? issueDate { get; set; }
        ///<summary>
        /// 版本
        ///</summary>
        [MaxLength(20)]
        public string version { get; set; }
        ///<summary>
        ///  版本变更描述
        ///</summary>
        [MaxLength(100)]
        public string description { get; set; }

        ///<summary>
        /// 有效日期
        ///</summary>
        public DateTime? validDate { get; set; }
        ///<summary>
        /// 核准
        ///</summary>
        [MaxLength(30)]
        public string approval { get; set; }
        ///<summary>
        /// 审核
        ///</summary>
        [MaxLength(30)]
        public string auditor { get; set; }
        ///<summary>
        /// 研发部门负责人
        ///</summary>
        [MaxLength(30)]
        public string rdDeptMaster { get; set; }
    }
}