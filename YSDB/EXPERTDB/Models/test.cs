using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace EXPERTDB.Models
{
    /// <summary>
    /// 科目表
    /// </summary>
    public class test
    {
        [Key]
        public int id { get; set; }
        [MaxLength(80)]
        public string remark { get; set; }

        [Required, MaxLength(30)]
        public string tname { get; set; }
    }
}
