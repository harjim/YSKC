using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class patent_plan : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 专利需求id(新增字段)
        /// </summary>
        public int? demandId { get; set; }
        /// <summary>
        /// 公司id
        /// </summary>
        public int customerId { get; set; }
        /// <summary>
        /// 专利号
        /// </summary>
        [MaxLength(50)]
        public string patentNo { get; set; }

        /// <summary>
        /// 申请名称
        /// </summary>
        [Required, MaxLength(200)]
        public string patentName { get; set; }
        /// <summary>
        /// 专利类型 '1': '发明专利','2': '实用新型', '3': '外观设计'
        /// </summary>
        public int patentType { get; set; }
        /// <summary>
        /// 单位类型 0：工矿企业，1：个人，2大专院校，3：科研单位，4：事业单位
        /// </summary>
        public int unitType { get; set; }

        /// <summary>
        /// 申请类型（版本）：0：高新版，1：保护版
        /// </summary>
        public int planType { get; set; }

        ///<summary>
        /// 年份
        ///<summary>
        public int year { get; set; }

        /// <summary>
        /// 项目ID 没有为-1(其他)
        /// </summary>
        public int projectId { get; set; }
        /// <summary>
        /// 身份证号码或统一信息代码
        /// </summary>
        [Required, MaxLength(50)]
        public string code { get; set; }

        /// <summary>
        /// 联系人
        /// </summary>
        [Required, MaxLength(50)]
        public string linkMan { get; set; }

        /// <summary>
        /// 联系方式
        /// </summary>
        [Required, MaxLength(100)]
        public string linkTel { get; set; }

        /// <summary>
        /// 邮政编码
        /// </summary>
        [MaxLength(10)]
        public string postalCode { get; set; }

        /// <summary>
        /// 其他事项（多选） 0：发明+实用新型同时申请 1：申请时办理提前公开请求 2：是否办理费减
        /// </summary>
        [MaxLength(10)]
        public string otherItem { get; set; }

        /// <summary>
        /// 详细地址
        /// </summary>
        [MaxLength(200)]
        public string address { get; set; }

        ///<summary>
        /// 第一发明人
        ///<summary>
        [MaxLength(200)]
        public string firstInventor { get; set; }

        ///<summary>
        /// 其他发明人
        ///<summary>
        [MaxLength(300)]
        public string otherInventor { get; set; }

        /// <summary>
        /// 工程师id
        /// </summary>
        public int engineerId { get; set; }

        ///<summary>
        /// 0：技术交底，1：交底审批，2：代理人分配，3：定稿，4：受理，5:受理缴费，6：授权，7授权缴费
        ///<summary>
        public int process { get; set; }

        ///<summary>
        /// 业务员
        ///<summary>
        public int? ownerId { get; set; }

        ///<summary>
        /// 申请人
        ///<summary>
        [MaxLength(200)]
        public string applicant { get; set; }
        ///<summary>
        /// 预计提交国知局时间
        ///<summary>
        public DateTime? submitDate { get; set; }
        ///<summary>
        /// 初稿需求时间
        ///<summary>
        public DateTime? demandDate { get; set; }
        ///<summary>
        /// 备注
        ///<summary>
        [MaxLength(200)]
        public string remark { get; set; }
        ///<summary>
        /// 代理商
        ///<summary>
        public int? masterId { get; set; }
        
        /// <summary>
        /// 定稿专利名称
        /// </summary>
        [MaxLength(200)]
        public string confirmName { get; set; }
        /// <summary>
        /// 定稿专利类型 '1': '发明专利','2': '实用新型', '3': '外观设计'
        /// </summary>
        public int? confirmType { get; set; }

    }
}