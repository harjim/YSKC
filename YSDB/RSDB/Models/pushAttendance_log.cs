using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class pushAttendance_log
    {
        [Key]
        public int id { get; set; }
        public int companyId { get; set; }
        [MaxLength(30)]
        public string enumber { get; set; }
        public DateTime month { get; set; }
        public DateTime createTime { get; set; }
        [MaxLength(500)]
        public string result { get; set; }

    }
}