using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class p_achievement : tablebase
    {
        [Key]
        public int id { get; set; }

        ///<summary>
        /// 成果名称
        ///</summary>
        [MaxLength(100), Required]
        public string achievementName { get; set; }
        public int projectId { get; set; }
        public int companyId { get; set; }
        public int year { get; set; }
        ///<summary>
        /// 最后上传时间
        ///</summary>
        public DateTime? lastUploadTime { get; set; }
        ///<summary>
        /// 文件数
        ///</summary>
        public int? fileCnt { get; set; }
        ///<summary>
        ///成果类型：0：专利，1：版权，2：集成电路布图设计，3：其他
        ///</summary>
        public int type { get; set; }
        ///<summary>
        ///成果来源：0：自主研发，1：受让、受赠、并购，2：其他
        ///</summary>
        public int source { get; set; }
        ///<summary>
        /// 转化形式： 0：自行投资转化，1：向他人转让该技术成果，2：许可他人使用该科技成果
        /// 3：以该科技成果作为合作条件，与他人共同实施转化，4：以该科技成果作价投资，折算股份或者出资比例
        ///</summary>
        public int converType { get; set; }
        ///<summary>
        /// 转化说明
        ///</summary>
        [MaxLength(2000)]
        public string description { get; set; }
    }
}