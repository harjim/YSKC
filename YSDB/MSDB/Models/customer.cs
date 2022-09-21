using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    /// <summary>
    /// 客户表
    /// </summary>
    public class customer
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 对应用户端公司Id
        /// </summary>
        public int? companyId { get; set; }
        /// <summary>
        /// 公司名
        /// </summary>
        [Required, MaxLength(50)]
        public String companyName { get; set; }
        /// <summary>
        /// 公司地址
        /// </summary>
        [MaxLength(100)]
        public String companyAddress { get; set; }
        /// <summary>
        /// 所在地，代码
        /// </summary>
        [Required, MaxLength(30)]
        public String addressCode { get; set; }
        /// <summary>
        /// 纳税人识别号
        /// </summary>
        [Required, MaxLength(30)]
        public String taxpayerId { get; set; }
        /// <summary>
        /// 统一社会码
        /// </summary>
        [Required, MaxLength(30)]
        public String creditCode { get; set; }
        /// <summary>
        /// 发票抬头
        /// </summary>
        [Required, MaxLength(50)]
        public string invoiceTitle { get; set; }
        /// <summary>
        /// 所属行业
        /// </summary>
        [MaxLength(30)]
        public String industryCode { get; set; }
        /// <summary>
        /// 主行业
        /// </summary>
        [MaxLength(10)]
        public string mainIndustry { get; set; }

        /// <summary>
        /// 联系人
        /// </summary>
        [MaxLength(20)]
        public String linkMan { get; set; }
        /// <summary>
        /// 联系电话
        /// </summary>
        [MaxLength(20)]
        public String linkTel { get; set; }

        /// <summary>
        /// 邮箱
        /// </summary>
        [MaxLength(100)]
        public string email { get; set; }
        /// <summary>
        /// 法人
        /// </summary>
        [Required, MaxLength(30)]
        public string owner { get; set; }
        /// <summary>
        /// 注册资金
        /// </summary>
        public int? capital { get; set; }
        /// <summary>
        /// 资金单位
        /// </summary>
        [MaxLength(50)]
        public string capitalUnit { get; set; }
        /// <summary>
        /// 公司注册时间
        /// </summary>
        public DateTime registerTime { get; set; }
        /// <summary>
        /// 首次发生研发费时间
        /// </summary>
        public DateTime? firstDevFee { get; set; }
        /// <summary>
        /// 会计制度
        /// </summary>
        [MaxLength(20)]
        public string accountSystem { get; set; }
        /// <summary>
        /// 所属税务机关
        /// </summary>
        [MaxLength(30)]
        public string taxAuthorities { get; set; }
        /// <summary>
        /// 办理税务机关
        /// </summary>
        [MaxLength(30)]
        public string realTaxAuthorities { get; set; }
        /// <summary>
        /// 是否有研发专用帐
        /// </summary>
        public bool hasDevAccount { get; set; }

        public bool highTec { get; set; }
        /// <summary>
        /// 高新技术领域
        /// </summary>
        [MaxLength(20)]
        public string highTecIndustry { get; set; }

        /// <summary>
        /// 0:用户注册，1：CRM，2：新增
        /// </summary>
        public byte from { get; set; }
        /// <summary>
        /// 创建ID
        /// </summary>
        public int creatorId { get; set; }
        public DateTime creatorTime { get; set; }

        public int lastUpdatorId { get; set; }
        public DateTime lastUpdateTime { get; set; }
        [MaxLength(200)]
        public string remark { get; set; }
        /// <summary>
        /// 所属部门
        /// </summary>
        public int? deptId { get; set; }
        /// <summary>
        /// 业务员id
        /// </summary>
        public int? owerId { get; set; }
        /// <summary>
        /// 公司类型： 0默认，1集团公司，2子公司
        /// </summary>
        public int companyType { get; set; }
        /// <summary>
        /// 所属集团id，默认为0，即不属于任何集团
        /// </summary>
        public int groupId { get; set; }
        /// <summary>
        /// 客户级别, 分为A B C三个等级.
        /// </summary>
        [MaxLength(10)]
        public string companyLevel { get; set; }
        
        /// <summary>
        /// 生效时间
        /// </summary>
        public DateTime? effectDate { get; set; }
        
        /// <summary>
        /// 类型：0:未签、10:邀约、20:拜访、30:已签
        /// </summary>
        public int status { get; set; }
    }
}
