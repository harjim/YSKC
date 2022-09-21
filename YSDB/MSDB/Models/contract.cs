using System;
using System.ComponentModel.DataAnnotations;

namespace MSDB.Models
{
    public class contract : tablebase
    {
        [Key]
        public int id { get; set; }

        public int customerId { get; set; }

        public int deptId { get; set; }

        ///<summary>
        /// 服务单号
        ///</summary>
        [Required, MaxLength(20)]
        public string contractNo { get; set; }

        ///<summary>
        /// 项目数
        ///</summary>
        public int projectCnt { get; set; }

        ///<summary>
        /// 合伙人数
        ///</summary>
        public int partnerCnt { get; set; }

        ///<summary>
        /// 签署日期
        ///</summary>
        public DateTime signDate { get; set; }

        ///<summary>
        ///生效日期(不能小于终止日期#closeDate)
        ///</summary>
        public DateTime effectDate { get; set; }

        ///<summary>
        ///终止日期
        ///</summary>
        public DateTime closeDate { get; set; }

        ///<summary>
        ///技术负责人
        ///</summary>
        public int techId { get; set; }

        ///<summary>
        ///财务负责人
        ///</summary>
        public int finaId { get; set; }

        ///<summary>
        ///文件路径(单文件)
        ///</summary>
        [Required,MaxLength(200)]
        public string filepath { get; set; }
        /// <summary>
        /// 二维码图片地址
        /// </summary>
        [MaxLength(100)]
        public string qrcode { get; set; }

        [MaxLength(200)]
        public string remark { get; set; }
        ///<summary>
        /// 印章类型：0：公章，1：法人章，2：财务章，3：合同章（多选）
        ///</summary>
        [Required,MaxLength(20)]
        public string sealType { get; set; }
        
        [MaxLength(20)]
        public string expressNo { get; set; }
        [MaxLength(100)]
        public string expressAddress { get; set; }
    }
}
