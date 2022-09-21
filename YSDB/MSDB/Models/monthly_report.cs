using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class monthly_report
    {
        [Key]
        public int id { get; set; }
        public int userId { get; set; }
        ///<sumamry>
        /// 工作月份
        ///<sumamry>
        public DateTime workMonth { get; set; }

        public DateTime createTime { get; set; }
        public DateTime updateTime { get; set; }
        public int done { get; set; }
        public int commit { get; set; }
        public int reject { get; set; }
        public int serviceCnt { get; set; }
        public int companyCnt { get; set; }
    }
}