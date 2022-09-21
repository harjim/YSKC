using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace ESDB.Models
{
    public class talent_requirement
    {
        [Key]
        public int id { get; set; }

        ///<summary>
        /// 职位名称
        /// </summary>
        [Required, MaxLength(100)]
        public string job { get; set; }

        ///<summary>
        /// 最小薪资（当只有最小薪资时，只显示最小薪资）
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? minSalary { get; set; }
        ///<summary>
        /// 最大薪资
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? maxSalary { get; set; }

        ///<summary>
        /// 面议(双方洽谈(价格))
        /// 为true时，salaries置空
        ///</summary>
        public bool negotiation { get; set; }
        ///<summary>
        /// 职位类型
        /// 0：技术，1：产品，2：设计，3：运营，4：市场，5：人事/财务/行政，6：高级管理，7：销售
        /// 8：传媒，9：金融，10：教育培训，11：医疗健康，12：采购/贸易，13：供应链/物流，14：房地产/建筑
        /// 15：农/林/牧/渔，16：咨询/翻译/法律，17：旅游，18：服务业，19：生产制造，99：不限
        /// </summary> 
        public int jobType { get; set; }
        ///<summary>
        /// 工作经验(年限) 0：1-3年，1：3-5年、2：5-10年，3：10年以上，4: 经验不限
        ///</summary>
        public int workYear { get; set; }
        ///<summary>
        /// 学历 0：研究生，1：本科，2：大专，3：学历不限（其他）
        ///</summary>
        public int eduLevel { get; set; }
        ///<summary>
        /// 招聘类型：0：社会招聘，1：校园招聘
        ///</summary>
        public int recruitment { get; set; }
        ///<summary>
        /// 截至日期
        ///</summary>
        public DateTime closeDate { get; set; }
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
        /// 岗位职责
        ///</summary>
        [Required, MaxLength(1000)]
        public string duty { get; set; }
        ///<summary>
        /// 岗位要求
        ///</summary>
        [Required, MaxLength(1000)]
        public string requirement { get; set; }
        ///<summary>
        ///状态：0：草稿，1：发布，4：终止
        /// status为1且closeDate小于当前日期时，显示为过期
        ///</summary>
        public int status { get; set; }
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