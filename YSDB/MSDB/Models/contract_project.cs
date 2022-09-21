using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace MSDB.Models
{
    public class contract_project : tablebase
    {
        [Key]
        public int id { get; set; }

        ///<summary>
        ///合同id
        ///</summary>
        public int contractId { get; set; }

        public int customerId { get; set; }

        /// <summary>
        /// 项目类型id
        /// </summary>
        public int productId { get; set; }

        ///<summary>
        /// 开始年
        ///</summary>
        public int beginYear { get; set; }
        ///<summary>
        ///结束年
        ///</summary>
        public int endYear { get; set; }
        
        ///<summary>
        ///比例
        ///</summary>
        [Required, Column(TypeName = "decimal(5,2)")]
        public decimal ratio { get; set; }

        ///<summary>
        ///签署次数
        ///</summary>
        public int signCnt { get; set; }

        [MaxLength(200)]
        public string remark { get; set; }
    }
}
