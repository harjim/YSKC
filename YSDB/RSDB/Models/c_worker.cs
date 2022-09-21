using System;
using System.ComponentModel.DataAnnotations;

namespace RSDB.Models
{
    public class c_worker
    {
        [Key]
        public int id { get; set; }
        public int companyId { get; set; }
        public int year { get; set; }
        ///<summary>
        ///优赛技术
        ///</summary>
        [MaxLength(30)]
        public string ysTech { get; set; }
        ///<summary>
        ///优赛财务
        ///</summary>
        [MaxLength(30)]
        public string ysFina { get; set; }
        public DateTime createTime { get; set; }
        public DateTime lastUpdateTime { get; set; }
    }
}