using System;
using System.ComponentModel.DataAnnotations;

namespace MSDB.Models
{
    public class service_apply : tablebase
    {
        [Key]
        public int id { get; set; }

        ///<summary>
        /// 服务单号
        ///</summary>
        [Required, MaxLength(20)]
        public string serviceNo { get; set; }

        ///<summary>
        /// 申请人（业务员）
        ///</summary>
        public int? ownerId { get; set; }

        ///<summary>
        /// 开始日期
        ///</summary>
        public DateTime begin { get; set; }

        ///<summary>
        ///结束日期
        ///</summary>
        public DateTime end { get; set; }

        ///<summary>
        ///备注
        ///</summary>
        [MaxLength(200)]
        public string remark { get; set; }
        ///<summary>
        ///客户（冗余客户名称，多个以英文逗号隔开，按添加顺序拼接）
        ///</summary>
        [Required,MaxLength(200)]
        public string customers { get; set; }
    }
}
