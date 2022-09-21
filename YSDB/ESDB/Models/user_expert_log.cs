using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;
namespace ESDB.Models
{
    public class user_expert_log
    {
        [Key]
        public int id { get; set; }
        ///<sumarry>
        /// 用户专家id
        ///</summary>
        public int userExpertId { get; set; }

        ///<sumarry>
        /// 状态：0：未审核，1：审核通过，2：驳回
        ///</summary>
        public int status { get; set; }
        [MaxLength(2000)]
        public string suggestion { get; set; }
        public DateTime createTime { get; set; }
        public int msCreatorId { get; set; }
    }
}