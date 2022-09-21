using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class h3_customer
    {
        /// <summary>
        /// 氚云Id
        /// </summary>
        [Key,MaxLength(48)]
        public string objectid{ get; set; }
        /// <summary>
        /// 客户名称
        /// </summary>
        [MaxLength(120)]
        public string name{ get; set; }
        [MaxLength(50)]
        public string createdBy{ get; set; }
        [MaxLength(20)]
        public string createdTime{ get; set; }
        [MaxLength(20)]
        public string modifiedBy{ get; set; }
        [MaxLength(20)]
        public string modifiedTime{ get; set; }
        [MaxLength(50)]
        public string workflowInstanceId{ get; set; }
        public int status{ get; set; }
        [MaxLength(120)]
        public string clientName{ get; set; }
        [MaxLength(48)]
        public string strategicLevel{ get; set; }
        [MaxLength(48)]
        public string clientLevel{ get; set; }
        [MaxLength(48)]
        public string salesRegion{ get; set; }
        [MaxLength(30)]
        public string contactName{ get; set; }
        [MaxLength(30)]
        public string mobile{ get; set; }
        /// <summary>
        /// 注册资金
        /// </summary>
        [MaxLength(48)]
        public string f0000003{ get; set; }
        /// <summary>
        /// 客户所在城市
        /// </summary>
        [MaxLength(60)]
        public string f0000027{ get; set; }
        /// <summary>
        /// 地址
        /// </summary>
        [MaxLength(200)]
        public string addr{ get; set; }
        /// <summary>
        /// 客户意向
        /// </summary>
        [MaxLength(200)]
        public string salesOpportunitie{ get; set; }
        /// <summary>
        /// 统一社会代码
        /// </summary>
        [MaxLength(30)]
        public string f0000022{ get; set; }
        /// <summary>
        /// 业务员
        /// </summary>
        [MaxLength(30)]
        public string ownerId{ get; set; }
        /// <summary>
        /// 所属部门
        /// </summary>
        [MaxLength(100)]
        public string ownerDeptId{ get; set; }
        /// <summary>
        /// 
        /// </summary>
        [MaxLength(60)]
        public string origin{ get; set; }
        [MaxLength(60)]
        public string bank{ get; set; }
        [MaxLength(60)]
        public string accountNumber{ get; set; }
        [MaxLength(150)]
        public string address{ get; set; }
        /// <summary>
        /// 纳税识别号
        /// </summary>
        [MaxLength(30)]
        public string taxID { get; set; }
        /// <summary>
        /// 公司开票抬头
        /// </summary>
        [MaxLength(120)]
        public string clientFPName { get; set; }
        /// <summary>
        /// 所属行业
        /// </summary>
        [MaxLength(60)]
        public string f0000038 { get; set; }
        [MaxLength(60)]
        public string salesAssistant { get; set; }
        /// <summary>
        /// 企业性质
        /// </summary>
        public string f0000039 { get; set; }
        /// <summary>
        /// 服务中心区域经理
        /// </summary>
        [MaxLength(30)]
        public string f0000029 { get; set; }
        /// <summary>
        /// 区域服务人员
        /// </summary>
        [MaxLength(30)]
        public string f0000033 { get; set; }
        /// <summary>
        /// 技术中心负责人
        /// </summary>
        public string f0000031 { get; set; }
        /// <summary>
        /// 技术工程师
        /// </summary>
        [MaxLength(30)]
        public string f0000034 { get; set; }
        /// <summary>
        /// 财务区域经理
        /// </summary>
        [MaxLength(30)]
        public string f0000036 { get; set; }
        /// <summary>
        /// 财务人员
        /// </summary>
        [MaxLength(30)]
        public string f0000037 { get; set; }
        /// <summary>
        /// 转换状态0：未转，1：已转
        /// </summary>
        public int convertStatus { get; set; }
        public int companyId { get; set; }
        public DateTime synDataDateTime { get; set; }
        /// <summary>
        /// 转化时间
        /// </summary>
        public DateTime? conversionDate { get; set; }
    }
}
