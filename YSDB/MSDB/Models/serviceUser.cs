using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    /// <summary>
    /// 服务人员表
    /// </summary>
    public class serviceUser : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 用户id
        /// </summary>
        public int userId { get; set; }
        /// <summary>
        /// 人员类型 0 主要服务人员 1其他服务人员
        /// </summary>
        public int mType { get; set; }
        ///<summary>
        ///客户服务id
        ///</summary>
        public int serviceId { get; set; }

    }
}
