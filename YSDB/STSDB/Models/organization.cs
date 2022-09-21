using System;
using System.ComponentModel.DataAnnotations;

namespace STSDB.Models
{
    public class organization
    {
        [Key]
        public int id { get; set; }

        ///<summary>
        ///组织名称
        ///</summary>
        [Required, MaxLength(50)]
        public string orgName { get; set; }

        /// <summary>
        /// 所在地，代码
        /// </summary>
        [MaxLength(30)]
        public string addressCode { get; set; }

        /// <summary>
        /// 公司地址
        /// </summary>
        [MaxLength(100)]
        public string addressDetail { get; set; }

        public DateTime createTime { get; set; }

        public DateTime lastUpdateTime { get; set; }
    }
}
