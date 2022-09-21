using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class p_adjust
    {
        [Key]
        public int id { get; set; }
        public int companyId { get; set; }

        public int projectId { get; set; }

        public int rdType { get; set; }

        [Column(TypeName = "decimal(18,2)")]
        public decimal amount { get; set; }

        public int pRdId { get; set; }
        
        public DateTime month { get; set; }

        /// <summary>
        /// 管理端用户
        /// </summary>
        public int msCreatorId { get; set; }

        /// <summary>
        /// 管理端用户
        /// </summary>
        public int msLastUpdatorId { get; set; }
        public DateTime createTime { get; set; }
        public DateTime lastUpdateTime { get; set; }

    }
}