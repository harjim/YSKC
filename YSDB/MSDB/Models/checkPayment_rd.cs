using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace MSDB.Models
{
    public class checkPayment_rd : tablebase
    {
        [Key]
        public int id { get; set; }
        
        ///<summary>
        ///查新付费id
        ///</summary>
        public int checkPaymentId { get; set; }
        ///<summary>
        ///项目名称 
        ///</summary>
        [MaxLength(200)]
        public string pname { get; set; }
        ///<summary>
        ///项目编号
        ///</summary>
        [Required, MaxLength(50)]
        public string rdTitle { get; set; }
    }
}
