using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class p_design : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 项目id
        /// </summary>
        public int projectId { get; set; }
        /// <summary>
        /// 设计数据id
        /// </summary>
        public int designDataId { get; set; }

        public int companyId { get; set; }

        [Column(TypeName = "decimal(18,2)")]
        public decimal rdAmount { get; set; }

    }
}
