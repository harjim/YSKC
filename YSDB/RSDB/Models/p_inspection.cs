using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 项目检测修理费用
    /// </summary>
    public class p_inspection : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 项目Id
        /// </summary>
        public int projectId { get; set; }
        /// <summary>
        /// 检测修理数据Id
        /// </summary>
        public int inspectionDataId { get; set; }

        public int companyId { get; set; }


        [Column(TypeName = "decimal(18,2)")]
        public decimal rdAmount { get; set; }

    }
}
