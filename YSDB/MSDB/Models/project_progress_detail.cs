using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class project_progress_detail
    {
        [Key]
        public int id { get; set; }

        public int companyId { get; set; }

        public int year { get; set; }
        public int userId { get; set; }

        public int stage { get; set; }

        ///<summary>
        /// 操作时间
        ///</summary>
        public DateTime operationTime { get; set; }

        [MaxLength(100)]
        public string result { get; set; }

        ///<summary>
        /// 更新本数据时间
        ///</summary>
        public DateTime updateTime { get; set; }
    }
}