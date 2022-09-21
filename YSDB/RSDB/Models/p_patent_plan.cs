using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class p_patent_plan : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 公司id
        /// </summary>
        public int companyId { get; set; }
        /// <summary>
        /// 项目ID 
        /// </summary>
        public int projectId { get; set; }
        /// <summary>
        /// 专利号
        /// </summary>
        [MaxLength(50)]
        public string patentNo { get; set; }

        /// <summary>
        /// 发明名称
        /// </summary>
        [Required, MaxLength(200)]
        public string patentName { get; set; }

        /// <summary>
        /// 交底书
        /// </summary>
        [Required, MaxLength(200)]
        public string disclosureParperPath { get; set; }
        [MaxLength(500)]
        public string description { get; set; }

        public int? masterId { get; set; }

        ///<summary>
        /// 0：待申请，1：申请中，2：完成，3：拒绝
        ///<summary>
        public int status { get; set; }

        ///<summary>
        /// 来源 0：新增，1：同步
        ///<summary>
        public int source { get; set; }

        ///<summary>
        /// 发明人
        ///<summary>
        [MaxLength(200)]
        public string inventor { get; set; }

        [MaxLength(1000)]
        public string filepath { get; set; }

        ///<summary>
        /// 发明人信息（附件）
        ///<summary>
        [MaxLength(1000)]
        public string inventorInfo { get; set; }

        ///<summary>
        /// 年份
        ///<summary>
        public int year { get; set; }
          ///<summary>
        /// 定稿名称
        ///<summary>
        [MaxLength(100)]
        public string finalizedName { get; set; }
    }
}