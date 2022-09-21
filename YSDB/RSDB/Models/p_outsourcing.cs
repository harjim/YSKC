using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 委外费用
    /// </summary>
    public class p_outsourcing : tablebase
    { 
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 公司id
        /// </summary>
        public int companyId { get; set; }
        /// <summary>
        /// 项目ID 
        /// </summary>
        public int projectId { get; set; }
        public DateTime month { get; set; }
        ///<summary>
        /// 0 国内委外，1 国外委外
        public int type {get; set;}
        /// <summary>
        /// rd费用
        /// </summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal rdFunds { get; set; }

    }
}
