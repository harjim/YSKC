using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;
namespace ESDB.Models
{
    public class achievement_log
    {
        [Key]
        public int id { get; set; }
        ///<summary>
        /// 成果id
        ///</summary>
        public int achievementId { get; set; }

        ///<summary>
        /// 状态：1：审核通过，2：不通过（驳回）
        ///</summary>
        public int status { get; set; }
        [MaxLength(2000)]
        public string suggestion { get; set; }
        public int msCreatorId { get; set; }
        public DateTime createTime { get; set; }
    }
}