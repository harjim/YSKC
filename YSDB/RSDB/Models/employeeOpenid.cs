using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class employeeOpenid
    {
        [Key]
        public int id { get; set; }
        public int companyId { get; set; }
        [MaxLength(50), Required]
        public string companyName { get; set; }
        [MaxLength(30), Required]
        public string enumber { get; set; }

        [MaxLength(100), Required]
        public string openid { get; set; }

        [MaxLength(100)]
        public string unionid { get; set; }

        ///<summary>
        /// 是否提醒考勤打卡
        /// </summary>
        public bool remainAttendance { get; set; }

        public DateTime createTime { get; set; }
        public DateTime lastUpdateTime { get; set; }

    }
}