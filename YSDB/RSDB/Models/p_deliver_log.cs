using System;
using System.ComponentModel.DataAnnotations;

namespace RSDB.Models
{
    public class p_deliver_log
    {
        [Key]
        public int id { get; set; }
        public int companyId { get; set; }
        public int deliverId { get; set; }

        public int status { get; set; }
        [MaxLength(2000)]
        public string suggestion { get; set; }
        ///<summary>
        /// 节点：0：优赛，10：工厂，20：区域
        ///</summary>
        public int node { get; set; }
        ///<summary>
        /// 日志时间
        ///</summary>
        public DateTime logTime { get; set; }
        ///<summary>
        /// 审核人
        ///</summary>
        [MaxLength(30), Required]
        public string auditUser { get; set; }
    }

}