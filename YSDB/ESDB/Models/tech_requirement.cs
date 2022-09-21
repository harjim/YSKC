using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace ESDB.Models
{
    public class tech_requirement
    {
        [Key]
        public int id { get; set; }
        ///<summary>
        ///需求状态：0：草稿，1：发布，2：服务中，3：完成，4：终止
        /// 请注意：，当status=1时且closeDate小于当前日期显示[过期]
        /// 不能发布closeDate小于当前日期的需求
        ///status=2/3时，不可编辑
        ///</summary>
        public int status { get; set; }
        ///<summary>
        /// 需求名称
        ///</summary>
        [Required, MaxLength(100)]
        public string requirementName { get; set; }
        ///<summary>
        /// 行业分类
        ///</summary>
        [Required, MaxLength(20)]
        public string industry { get; set; }
        ///<summary>
        /// 领域分类
        ///</summary>
        [Required, MaxLength(20)]
        public string researchArea { get; set; }
        ///<summary>
        /// 合作方式:
        ///0：技术转让，1：技术服务，2：技术许可
        ///3：技术融资，4：技术授权，5：其他
        ///</summary>
        public int cooperateType { get; set; }
        ///<summary>
        /// 紧急程度 0:一周以内 1:一月以内 2:三个月以内 3:六个月以内 4:一年以内 5:两年以上
        ///</summary>
        public int urgency { get; set; }
        ///<summary>
        /// 截至日期
        ///</summary>
        public DateTime closeDate { get; set; }
        ///<summary>
        /// 需求预算（万元）[当negotiation为false时必须录入]
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? budget { get; set; }
        
        ///<summary>
        /// 双方洽谈（价格）
        ///</summary>
        public bool negotiation { get; set; }
        ///<summary>
        /// 关键字【最多三个，用逗号隔开。存储形式：,xxx,xxx,xxx,  显示：xxx,xxx,xxx 查询：like %,{xxx},%】
        ///</summary>
        [Required, MaxLength(50)]
        public string keywords { get; set; }
        ///<summary>
        /// 客户名称
        ///</summary>
        [Required, MaxLength(100)]
        public string customerName { get; set; }
        ///<summary>
        ///联系人
        ///</summary>
        [Required, MaxLength(20)]
        public string linkName { get; set; }
        ///<summary>
        /// 职位（部门职务）
        ///</summary>
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
        ///<summary>
        /// 地址码
        ///</summary>
        [Required, MaxLength(30)]
        public string addressCode { get; set; }
        ///<summary>
        /// 详细地址
        ///</summary>
        [Required, MaxLength(200)]
        public string address { get; set; }
        ///<summary>
        ///项目背景
        ///</summary>
        [MaxLength(2000)]
        public string background { get; set; }
        ///<summary>
        ///技术难点
        ///</summary>
        [MaxLength(2000)]
        public string difficulty { get; set; }
        ///<summary>
        ///指标/预期效果
        ///</summary>
        [MaxLength(2000)]
        public string target { get; set; }

        public DateTime createTime { get; set; }
        public DateTime lastUpdateTime { get; set; }
        /// <summary>
        /// 管理端用户
        /// </summary>
        public int msCreatorId { get; set; }
        /// <summary>
        /// 管理端用户
        /// </summary>
        public int msLastUpdatorId { get; set; }

    }
}