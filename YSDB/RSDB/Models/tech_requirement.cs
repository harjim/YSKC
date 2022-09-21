using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    ///技术需求表
    /// </summary>
    public class tech_requirement : tablebase
    {
        [Key]
        public int id { get; set; }

        public int companyId { get; set; }
        /// <summary>
        /// 联系人
        /// </summary>
        [Required, MaxLength(20)]
        public string linkName { get; set; }
        /// <summary>
        /// 职务
        /// </summary>
        [Required, MaxLength(50)]
        public string position { get; set; }
        /// <summary>
        ///  联系电话
        /// </summary>
        [Required, MaxLength(50)]
        public string linkTel { get; set; }
        /// <summary>
        ///  联系邮箱
        /// </summary>
        [Required, MaxLength(50)]
        public string linkEmail { get; set; }
        /// <summary>
        ///  简介
        /// </summary>
        [Required, MaxLength(300)]
        public string description { get; set; }
        /// <summary>
        ///  附件地址
        /// </summary>
        [Required, MaxLength(500)]
        public string filePath { get; set; }
        /// <summary>
        ///  技术需求名称
        /// </summary>
        [Required, MaxLength(300)]
        public string techName { get; set; }

        /// <summary>
        ///  技术领域
        /// </summary>
        [Required, MaxLength(50)]
        public string domain { get; set; }

        /// <summary>
        ///  投资金额
        /// </summary> 
        [Column(TypeName = "decimal(18,2)")]
        public decimal investment { get; set; }
        /// <summary>
        ///  需求描述
        /// </summary>
        [Required, MaxLength(300)]
        public string requirement { get; set; }

        /// <summary>
        ///  合作方式
        /// </summary>
        [Required, MaxLength(50)]
        public string cooperateType { get; set; }

        public int year { get; set; }

        /// <summary>
        ///  其他领域
        /// </summary>
        [MaxLength(100)]
        public string otherDomain { get; set; }
        /// <summary>
        ///  其他合作方式
        /// </summary>
        [MaxLength(100)]
        public string otherCooperateType { get; set; }
        /// <summary>
        ///  状态:0 普通, 1 作废
        /// </summary>
        public int status { get; set; }
        /// <summary>
        ///  合作院校
        /// </summary>
        [MaxLength(50)]
        public string cooperateSchool { get; set; }
    }
}
