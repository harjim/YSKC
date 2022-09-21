using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class flowInstance_user : tablebase
    {
        [Key]
        public int id { get; set; }

        public int instanceId { get; set; }

        public int userId { get; set; }

        public int nodeId { get; set; }

        ///<summary>
        /// 会签节点审核状态
        ///</summary>
        public bool? status { get; set; }
    }
}
