using System;
using System.ComponentModel.DataAnnotations;

namespace RSDB.Models
{
    ///<summary>
    /// 科技管理人员
    ///</summary>
    public class stEmployee : tablebase
    {
        [Key]
        public int id { get; set; }

        public int companyId { get; set; }

        public int year { get; set; }

        [Required, MaxLength(30)]
        public string enumber { get; set; }
    }
}
