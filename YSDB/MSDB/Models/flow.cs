using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class flow
    {
        [Key]
        public int id { get; set; }
        [Required, MaxLength(50)]
        public string name { get; set; }
        ///<summary>
        /// 0原子流程，1子流程（分支流程）
        ///</summary>
        public int type { get; set; }

        [MaxLength(200)]
        public string remark { get; set; }

        public DateTime createTime { get; set; }
    }
}