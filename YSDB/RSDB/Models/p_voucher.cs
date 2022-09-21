using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class p_voucher : tablebase
    {

        [Key]
        public int id { get; set; }
        public int companyId { get; set; }

        ///<summary>
        /// 凭证号
        ///</summary>
        [Required, MaxLength(50)]
        public string voucherNo { get; set; }

        public DateTime month { get; set; }

        ///<summary>
        /// 费用类型
        ///</summary>
        public int rdType { get; set; }

        public int projectId { get; set; }

    }
}