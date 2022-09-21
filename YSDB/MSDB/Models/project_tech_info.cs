using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class project_tech_info: tablebase
    {
        [Key]
        public int id { get; set; }

        public int projectId { get; set; }

         ///<summary>
        /// 启动时间
        /// </summary>
        public DateTime? startTime { get; set; }
         ///<summary>
        /// 对接时间
        /// </summary>
        public DateTime? dockingTime { get; set; }

         /// <summary>
        /// 服务内容
        /// </summary>
        public string content { get; set; }
      
    }
}
