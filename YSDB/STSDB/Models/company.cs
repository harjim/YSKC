using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace STSDB.Models
{
    public class company : tablebase
    {
        [Key]
        public int id { get; set; }

        public int orgId { get; set; }

        [Required, MaxLength(50)]
        public string companyName { get; set; }

        ///<summary>
        ///0.小于500万元（含）
        ///1.500万元以上~2000万元（含）
        ///2.2000万元以上~5000万元（含）
        ///3.5000万元以上~1亿元（含）
        ///4.1亿元以上~2亿元（含）
        ///5.2亿元以上~4亿元（含）
        ///6.4亿元以上
        public int? scale { get; set; }

        ///<summary>
        /// 注册日期(时间)
        ///</summary>
        public DateTime? registerDate { get; set; }

        /// <summary>
        /// 注册资金
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? capital { get; set; }

        /// <summary>
        /// 资金单位
        /// </summary>
        [MaxLength(10)]
        public string capitalUnit { get; set; }

        /// <summary>
        /// 所在地，代码
        /// </summary>
        [MaxLength(30)]
        public string addressCode { get; set; }

        /// <summary>
        /// 公司地址
        /// </summary>
        [MaxLength(100)]
        public string addressDetail { get; set; }

        /// <summary>
        /// 所属行业
        /// </summary>
        [MaxLength(30)]
        public string industryCode { get; set; }

        /// <summary>
        /// 是否上市
        /// </summary>
        public bool? listed { get; set; }

        /// <summary>
        /// 上市代码
        /// </summary>
        [MaxLength(20)]
        public string listedCode { get; set; }

        /// <summary>
        /// 是否高新
        /// </summary>
        public bool? highTech { get; set; }

        /// <summary>
        /// （高新）证书编号
        /// </summary>
        [MaxLength(30)]
        public string highTechCode { get; set; }

        /// <summary>
        /// 高新复查时间
        /// </summary>
        [MaxLength(30)]
        public DateTime? reviewDate { get; set; }

        /// <summary>
        /// 科技型中小企业
        /// </summary>
        public bool? smes { get; set; }

        /// <summary>
        /// 联系人
        /// </summary>
        [MaxLength(20)]
        public string linkMan { get; set; }

        /// <summary>
        /// 联系方式(电话)
        /// </summary>
        [MaxLength(20)]
        public string linkTel { get; set; }

        /// <summary>
        /// 电子邮箱
        /// </summary>
        [MaxLength(50)]
        public string email { get; set; }

        /// <summary>
        /// 财务负责人
        /// </summary>
        [MaxLength(20)]
        public string finaChief { get; set; }

        /// <summary>
        /// 财务负责人联系方式
        /// </summary>
        [MaxLength(20)]
        public string finaChiefTel { get; set; }

        /// <summary>
        /// 纳税人识别号
        /// </summary>
        [Required, MaxLength(30)]
        public string taxpayerId { get; set; }
    }
}
