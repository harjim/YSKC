using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;
namespace RSDB.Models
{
    public class e_bank_info : tablebase
    {
        [Key]
        public int id { get; set; }

        public int eUserId { get; set; }
        ///<summary>
        /// 银行名称(银行名称 + 支行)
        ///</summary>
        [MaxLength(100)]
        public string bankName { get; set; }
        ///<summary>
        /// 银行账户
        ///</summary>
        [MaxLength(50)]
        public string bankAccount { get; set; }
        ///<summary>
        /// 开户名
        ///</summary>
        [MaxLength(50)]
        public string accountName { get; set; }
        [MaxLength(10)]
        public string postCode { get; set; }
        [MaxLength(200)]
        public string remitAddress { get; set; }
        [MaxLength(100)]
        public string idFront { get; set; }
        [MaxLength(100)]
        public string idBack { get; set; }
        [MaxLength(100)]
        public string diplomaPath { get; set; }
        [MaxLength(100)]
        public string titlePath { get; set; }
        [MaxLength(100)]
        public string positionPath { get; set; }

    }
}