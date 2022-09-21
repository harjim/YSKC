using System;
using System.ComponentModel.DataAnnotations;

namespace STSDB.Models
{
    public class sys_log
    {
        [Key]
        public int id { get; set; }

        public int userId { get; set; }

        [MaxLength(100), Required]
        public string username { get; set; }
        ///<summary>
        /// 用户类型，0：org，1：com
        ///</summary>
        public int userType { get; set; }

        public DateTime logTime { get; set; }

        [MaxLength(100), Required]
        public string logUrl { get; set; }

        [MaxLength(4000)]
        public string logParams { get; set; }

        [MaxLength(30)]
        public string requestIp { get; set; }

        [MaxLength(100), Required]
        public string description { get; set; }
    }
}
