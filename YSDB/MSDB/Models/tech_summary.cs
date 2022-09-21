using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class tech_summary
    {
        [Key]
        public int id { get; set; }

        public int projectId { get; set; }

        public int customerId { get; set; }

        public int year { get; set; }
        public DateTime createTime { get; set; }

        public DateTime updateTime { get; set; }

        public DateTime? operationTime { get; set; }
        public int? operationUserId { get; set; }

        ///<summary>
        /// 阶段数
        ///</summary>
        public int stageCount { get; set; }
        ///<summary>
        /// 应上传数
        ///</summary>
        public int uploadCount { get; set; }
        ///<summary>
        /// 已上传数
        ///</summary>
        public int uploadedCount { get; set; }
    }
}