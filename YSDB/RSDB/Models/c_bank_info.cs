using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    ///<summary>
    /// 银行账户信息
    ///</summary>
    public class c_bank_info : tablebase
    {
        [Key]
        public int id { get; set; }
        public int companyId { get; set; }
        public int year { get; set; }
        ///<summary>
        /// 开户行
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
        ///<summary>
        /// 信用等级 ,0:AAA,1:AA,2:A,3:BBB,4:BB,5:B,6:CCC,7:CC,8:C,9:D
        ///</summary>
        public int? creditRating { get; set; }
        [MaxLength(50)]
        public string industry { get; set; }
        [MaxLength(200)]
        public string filePath { get; set; }
    }
}