using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;
namespace ESDB.Models
{
    public class achievement : tablebase
    {
        [Key]
        public int id { get; set; }

        public int userId { get; set; }

        ///<summary>
        /// 状态：0：审核中，1：审核通过（已发布），2：不通过（驳回），3：撤回，5：草稿
        /// 0,1,2,5遵循流程实例状态设计，3为业务本身所需状态编号。
        ///状态流程：（通过）5/2/3->0->1->3->0...，（驳回）5/2/3->0->2->0...
        ///状态为5,2,3时，开始一轮审核发布流程
        ///</summary>
        public int status { get; set; }
        ///<summary>
        /// 成果名称
        /// </summary>
        [MaxLength(100), Required]
        public string achievementName { get; set; }
        ///<summary>
        /// 行业分类
        ///</summary>
        [MaxLength(20), Required]
        public string industry { get; set; }
        ///<summary>
        /// 领域分类
        ///</summary>
        [MaxLength(20), Required]
        public string researchArea { get; set; }

        ///<summary>
        /// 成果类型: 0：发明专利，1：实用新型专利，2：软件著作权，3：著作权，4：商标权，5：新品种，6：外观设计，7：新技术
        ///</summary>
        public int type { get; set; }

        ///<summary>
        /// 技术成熟度: 1：1级-基本原理被发现或报告，2：2级-技术概念或用途被阐明，3：3级-关键功能或特征的概念验证
        /// 4：4级-实验室环境下的部件或实验模型验证，5：5级-相关环境下的部件或时间模型验证，6：6级-相关环境下的系统/子系统模型或样机验证
        /// 7：7级-模拟使用环境下的系统样机验证，8：8级-实际系统完成实验验证，9：9级-实际系统完成使用验证
        ///</summary>
        public int trl { get; set; }
        ///<summary>
        /// 资助情况：0：863，1：973，2：科技重大项目，3：自然科学基金，4：国家科技支撑计划，5：科技型中小企业创新基金，6：其他
        ///</summary>
        public int fundingType { get; set; }

        ///<summary>
        ///起止日期
        ///</summary>
        public DateTime startDate { get; set; }
        public DateTime endDate { get; set; }

        ///<summary>
        /// 是否协商（价格）
        ///</summary>
        public bool negotiation { get; set; }

        ///<summary>
        /// 意向价格（标价）[当negotiation为false时必须录入]
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? price { get; set; }

        ///<summary>
        /// 合作方式:
        ///0：技术转让，1：技术服务，2：技术许可
        ///3：技术融资，4：技术授权，5：其他
        ///</summary>
        public int cooperateType { get; set; }

        ///<summary>
        /// 关键字【最多三个，用逗号隔开。存储形式：,xxx,xxx,xxx,  显示：xxx,xxx,xxx 查询：like %,{xxx},%】
        ///</summary>
        [MaxLength(50), Required]
        public string keywords { get; set; }

        ///<summary>
        /// 成果来源
        ///</summary>
        [MaxLength(100), Required]
        public string source { get; set; }

        ///<summary>
        /// 部门职务（职位）
        ///</summary>
        [MaxLength(50), Required]
        public string job { get; set; }
        ///<summary>
        /// 持有人(所属人)【可以是】
        ///</summary>
        [MaxLength(50), Required]
        public string ownerName { get; set; }

        ///<summary>
        /// 联系电话
        ///</summary>
        [MaxLength(20), Required]
        public string tel { get; set; }
        ///<summary>
        /// 邮箱地址
        ///</summary>
        [MaxLength(50), Required]
        public string email { get; set; }
        ///<summary>
        /// 所在地区（code码）
        ///</summary>

        [MaxLength(30), Required]
        public string addressCode { get; set; }
        ///<summary>
        /// 详细地址
        ///</summary>
        [MaxLength(100), Required]
        public string address { get; set; }

        ///<summary>
        /// 成果描述
        ///</summary>
        [MaxLength(2000), Required]
        public string description { get; set; }

        ///<summary>
        /// 技术/产品创新型
        ///</summary>
        [MaxLength(2000), Required]
        public string innovation { get; set; }
    }
}