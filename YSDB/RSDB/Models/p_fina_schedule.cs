using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 财务排期表
    /// </summary>
    public class p_fina_schedule : tablebase
    {
        [Key]
        public int id { get; set; }
        public int projectId { get; set; }
        public int companyId { get; set; }
        public DateTime month { get; set; }
        [Column(TypeName = "decimal(18,2)")]
        public decimal? workHours { get; set; }
        [Column(TypeName = "decimal(18,2)")]
        ///<summary>
        /// 实验工时
        ///</summary>
        public decimal? testHour { get; set; }
        [Column(TypeName = "decimal(18,2)")]
        ///<summary>
        /// 试制工时
        ///</summary>
        public decimal? trialHour { get; set; }
        ///<summary>
        /// 部门
        ///</summary>
        [MaxLength(100)]
        public string deptName { get; set; }

    }
}
