using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class flowNode_user
    {
        [Key]
        public int id { get; set; }

        public int nodeId { get; set; }

        public int? dataId { get; set; }

        /// <summary>
        /// 创建人
        /// </summary>
        public int creatorId { get; set; }

        public DateTime createTime { get; set; }
        ///<summary>
        /// 0用户，1角色，2主管，3多层级角色, 4 指定人员(流程进行中指定)
        ///</summary>
        public int type { get; set; }
        ///<summary>
        /// type = 3时，层级有用
        ///</summary>
        public int? level { get; set; }

        ///<summary>
        /// 备注
        ///</remark>
        [MaxLength(100)]
        public string remark { get; set; }

    }
}