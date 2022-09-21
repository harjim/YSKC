using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class project_finance_info: tablebase
    {
        [Key]
        public int id { get; set; }
  

        public int projectId { get; set; }
        ///<summary>
        /// 对接时间
        /// </summary>
        public DateTime? dockingTime { get; set; }

         ///<summary>
        /// 预计归集研发总额
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? estimateRdAmount { get; set; }
     
    }
}