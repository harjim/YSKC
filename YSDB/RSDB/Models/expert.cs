using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 专家表
    /// </summary>
    public class expert
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 编号
        /// </summary>
        [Required, MaxLength(30)]
        public string expertNumber { get; set; }
        /// <summary>
        /// 姓名
        /// </summary>
        [Required, MaxLength(15)]
        public string realName { get; set; }
        /// <summary>
        /// 照片路径
        /// </summary>
        [Required, MaxLength(100)]
        public string photoUrl { get; set; }
        public DateTime birthday { get; set; }
        /// <summary>
        /// 专家级别，0: 特聘专家，1：高级研究员
        /// </summary>
        public int level { get; set; }
        /// <summary>
        /// 有效期
        /// </summary>
        public DateTime? validDate { get; set; }
        /// <summary>
        /// 发证日期
        /// </summary>
        public DateTime? issueDate { get; set; }
        /// <summary>
        /// 身份证
        /// </summary>
        [Required, MaxLength(30)]
        public string idNumber { get; set; }
        /// <summary>
        /// 性别，0：未知 ，1：男，2：女
        /// </summary>
        public byte gender { get; set; }
        /// <summary>
        /// 所属行业
        /// </summary>
        [Required, MaxLength(20)]
        public String industryCode { get; set; }
        /// <summary>
        /// 电话
        /// </summary>
        [Required, MaxLength(30)]
        public string phone { get; set; }
        /// <summary>
        /// 邮箱
        /// </summary>
        [MaxLength(30)]
        public string email { get; set; }
        /// <summary>
        /// 政治面貌
        /// </summary>
        public byte policitalStatus { get; set; }
        /// <summary>
        /// 学历， 0:无，1：高中及以下，2：中专，3：大专，4：本科 5：硕士，6：博士
        /// </summary>
        public int eduLevel { get; set; }
        /// <summary>
        /// 通讯地址
        /// </summary>
        [Required, MaxLength(100)]
        public string address { get; set; }
        /// <summary>
        /// 邮编
        /// </summary>
        public string postcode { get; set; }
        /// <summary>
        /// 工作经历
        /// </summary>
        [Required, MaxLength(3000)]
        public string workHistory { get; set; }

        /// <summary>
        /// 社会活动 及兼职情况
        /// </summary>
        [Required, MaxLength(3000)]
        public string partHistory { get; set; }
        /// <summary>
        /// 获得荣誉
        /// </summary>
        [MaxLength(3000)]
        public string honour { get; set; }
        /// <summary>
        /// 审核状态，0:待审核，1：审核通过，2：审核未通过
        /// </summary>
        public byte status { get; set; }

        [MaxLength(200)]
        public string remark { get; set; }
        /// <summary>
        /// 专家标识Id,添加的时候系统生成
        /// </summary>
        [Required, MaxLength(50)]
        public string uuid { get; set; }
        /// <summary>
        /// 二维码图片地址
        /// </summary>
        [Required, MaxLength(100)]
        public string qrcode { get; set; }
        /// <summary>
        /// 创建ID
        /// </summary>
        public int creatorId { get; set; }
        public DateTime createTime { get; set; }

        public int lastUpdatorId { get; set; }
        public DateTime lastUpdateTime { get; set; }
    }
}
