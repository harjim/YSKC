using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace EXPERTDB.Models
{
    public class e_paper : tablebase
    {
        [Key]
        public int id { get; set; }
        public int eUserId { get; set; }
        [MaxLength(50), Required]
        public string heading { get; set; }
        public DateTime issueDate { get; set; }

        [MaxLength(50), Required]
        public string journalName { get; set; }
        ///<summary>
        ///被何种文献数据库收录
        ///</summary>
        [MaxLength(100)]
        public string dataIncluded { get; set; }
        ///<summary>
        ///引入次数
        ///</summary>
        public int? includedCnt { get; set; }
    }
}