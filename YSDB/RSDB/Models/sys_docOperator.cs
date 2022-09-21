using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class sys_docOperator: tablebase
    {
        [Key]
        public int id { get; set; }

        /// <summary>
        /// 公司ID
        /// </summary>
        public int companyId { get; set; }


        public int listId { get; set; }

        [Required, MaxLength(50)]
        public string operators { get; set; }
}
}
