using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;
namespace MSDB.Models
{
    public class operation_log
    {
        [Key]
        public int id { get; set; }

        public int userId { get; set; }
        ///<summary>
        /// 操作日期
        ///</summary>
        public DateTime operationDate { get; set; }

        ///<summary>
        /// 最后操作时间
        ///</summary>
        public DateTime lastOperationTime { get; set; }

        ///<summary>
        /// 操作次数
        ///</summary>
        public int operationCnt { get; set; }
    }
}