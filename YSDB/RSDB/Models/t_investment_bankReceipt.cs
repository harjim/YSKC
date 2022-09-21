using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;
namespace RSDB.Models
{
    
    /// <summary>
    /// 投资清单-银行水单 关联表(已废弃)
    /// </summary>
    public class t_investment_bankReceipt : tablebase
    {
        [Key]
        public int id { get; set; }
        public int investmentId { get; set; }
        public int bankReceiptId { get; set; }
    }
}