using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace ESDB.Models
{
    ///<summary>
    /// 人才简历投递
    ///</summary>
    public class talent_delivery
    {
        [Key]
        public int id { get; set; }
        ///<summary>
        /// 用户id
        ///</summary>
        public int userId { get; set; }
        ///<summary>
        /// 人才需求id
        ///</summary>
        public int talentId { get; set; }
        public DateTime createTime { get; set; }
        public DateTime lastUpdateTime { get; set; }
    }
}