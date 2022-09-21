using System;
using System.ComponentModel.DataAnnotations;

namespace MSDB.Models
{
    ///<summary>
    /// 未归集项目
    ///</summary>
    public class rsProject_uncollected
    {
        [Key]
        public int id { get; set; }
        public int rsProjectId { get; set; }
        public int companyId { get; set; }
        public int year { get; set; }
        [MaxLength(50)]
        public string rdTitle { get; set; }
        [MaxLength(200)]
        public string pname { get; set; }
        public DateTime createTime { get; set; }
        public DateTime lastUpdateTime { get; set; }
    }
}