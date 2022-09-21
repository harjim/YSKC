using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class daily_report_detail
    {
        [Key]
        public int id { get; set; }
        public int userId { get; set; }
        ///<sumamry>
        /// 工作日期
        ///<sumamry>
        public DateTime workDate { get; set; }

        public DateTime createTime { get; set; }
        public DateTime updateTime { get; set; }
        public int done { get; set; }
        public int commit { get; set; }
        public int reject { get; set; }
        public int moduleId { get; set; }
        public int companyId { get; set; }

    }
}