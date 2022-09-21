using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class solution_share : tablebase
    {
        [Key]
        public int id { get; set; }
        ///<summary>
        /// 类型：0个人，1 部门
        ///</summary>
        public int type { get; set; }
        ///<summary>
        /// 方案id
        ///</summary>
        public int solutionId { get; set; }
        ///<summary>
        /// 数据id
        ///</summary>
        public int dataId { get; set; }

    }
}
