using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    /// <summary>
    /// 钉钉用户关联表
    /// </summary>
    public class project_summary
    {
        [Key]
        public int id { get; set; }

        public int projectId { get; set; }
        public int customerId { get; set; }

        public int? cnt { get; set; }

        public int? rdCount { get; set; }

        [Column(TypeName = "decimal(18,2)")]
        public decimal? budget { get; set; }
        [Column(TypeName = "decimal(18,2)")]
        public decimal? rdFunds { get; set; }
        public int? docFileCount { get; set; }

        public DateTime createTime { get; set; }

        public DateTime updateTime { get; set; }

        public DateTime? operationTime { get; set; }
        public int? operationUserId { get; set; }
        public int year { get; set; }

        public int? employeeCount { get; set; }

        public int? equipmentCount { get; set; }

        public int? rdEmployeeCount { get; set; }

        public int? rdEquipmentCount { get; set; }
    }
}
