using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class customer_trace
    {
        [Key]
        public int id { get; set; }

        public int customerId { get; set; }
        ///<summary>
        ///类型：1：添加客户，2：变更业务员，3：逾期未跟进，4：变更名称，10：邀约，20：拜访，30：已签
        ///</summary>
        public int type { get; set; }
        ///<summary>
        ///跟进信息，跟进情况
        ///</summary>
        [MaxLength(500)]
        public string info{ get; set; }
        ///<summary>
        ///创建人
        ///</summary>
        public int creatorId { get; set; }
        ///<summary>

        ///创建时间
        ///</summary>
        public DateTime createTime { get; set; }
    }
}