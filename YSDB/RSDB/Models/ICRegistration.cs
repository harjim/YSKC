using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class ICRegistration : tablebase
    {

        [Key]
        public int id { get; set; }
        public int companyId { get; set; }
        public int? projectId { get; set; }
        ///<summary>
        /// 设计名称
        ///</summary>
        [Required, MaxLength(200)]
        public string desginName { get; set; }
        ///<summary>
        /// 设计登记号
        ///</summary>
        [Required, MaxLength(50)]
        public string registerNo { get; set; }
        ///<summary>
        /// 申请日期
        ///</summary>
        public DateTime applyDate { get; set; }

        ///<summary>
        /// 权利人名称
        ///</summary>
        [MaxLength(200)]
        public string ownerName { get; set; }

        ///<summary>
        /// 权利人地址
        ///</summary>
        [MaxLength(200)]
        public string ownerAddress { get; set; }

        ///<summary>
        /// 颁证日期
        ///</summary>
        public DateTime issueDate { get; set; }
        ///<summary>
        /// 登记证书号
        ///</summary>
        [MaxLength(50)]
        public string certificateNo { get; set; }

        [MaxLength(1000)]
        public string filepath { get; set; }
        /// <summary>
        /// 0:自主 1：购买
        /// </summary>
        public int source { get; set; }

        /// <summary>
        /// 权利要求数量
        /// </summary>
        public int? claimNum { get; set; }
        /// <summary>
        /// 权利要求内容
        /// </summary>
        [MaxLength(1000)]
        public string claimContent { get; set; }

        /// <summary>
        /// 说明书内容
        /// </summary>
        [MaxLength(2000)]
        public string specification { get; set; }
        /// <summary>
        /// 使用次数
        /// </summary>
        public int? usedCnt { get; set; }
    }
}