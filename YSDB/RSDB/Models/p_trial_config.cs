using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class p_trial_config : tablebase
    {
        [Key]
        public int id { get; set; }
        public int companyId { get; set; }
        public System.TimeSpan trialTime { get; set; }
        /// <summary>
        /// 开始休息时间
        /// </summary>
        public System.TimeSpan? startTime { get; set; }
        /// <summary>
        ///结束休息时间
        /// </summary>
        public System.TimeSpan? endTime { get; set; }

    }
}