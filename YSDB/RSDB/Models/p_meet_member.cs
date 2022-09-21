using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class p_meet_member : tablebase
    {
        [Key]
        public int id { get; set; }

        public int projectId { get; set; }
        /// <summary>
        /// 参会人员
        /// </summary>
        [MaxLength(2000)]
        public string members { get; set; }
    }
}