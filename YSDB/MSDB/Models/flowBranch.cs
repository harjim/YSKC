using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class flowBranch : tablebase
    {
        [Key]
        public int id { get; set; }
        public int nodeId { get; set; }

        [Required, MaxLength(200)]
        public string condition { get; set; }

        public int? flowId { get; set; }

        public int seq { get; set; }
        ///<summary>
        /// 类型：0部门
        ///</summary>
        public int type { get; set; }

    }
}